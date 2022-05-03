package com.votingapp.configuration.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votingapp.domain.UserDetailsSecurity;
import com.votingapp.domain.dto.CredentialsDTO;
import com.votingapp.utils.JWTUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthenticatorFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    public JWTAuthenticatorFilter(AuthenticationManager authenticationManager, JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CredentialsDTO credentialsDTO = new ObjectMapper()
                    .readValue(request.getInputStream(), CredentialsDTO.class);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(), credentialsDTO.getPassword(), new ArrayList<>());

            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request,
                                       HttpServletResponse response,
                                       FilterChain chain,
                                       Authentication authentication) {

        String username = ((UserDetailsSecurity) authentication.getPrincipal()).getUsername();
        String token = jwtUtils.generateToken(username);
        response.addHeader("Authorization", String.format("Bearer %s", token));
    }
}
