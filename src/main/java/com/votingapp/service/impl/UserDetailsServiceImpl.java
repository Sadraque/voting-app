package com.votingapp.service.impl;

import com.votingapp.domain.User;
import com.votingapp.domain.UserDetailsSecurity;
import com.votingapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> object= userRepository.findByEmail(username);

        if (object.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        User user = object.get();

        return UserDetailsSecurity.builder()
                .id(user.getId())
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(userRole -> new SimpleGrantedAuthority(userRole.getRole()))
                                .collect(Collectors.toList()))
                .build();

    }
}
