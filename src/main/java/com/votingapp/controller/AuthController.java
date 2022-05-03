package com.votingapp.controller;

import com.votingapp.domain.dto.ForgotPasswordResponseDTO;
import com.votingapp.domain.dto.RecoverPasswordDTO;
import com.votingapp.service.ISecurityService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final ISecurityService securityService;

    @PostMapping("refresh")
    public ResponseEntity refreshToken(HttpServletResponse httpServletResponse) {
        httpServletResponse.addHeader("Authorization", String.format("Bearer %s", securityService.refreshToken()));

        return noContent();
    }

    @PostMapping("forgot")
    public ResponseEntity resetPassword(@Valid @RequestParam String email) {
        ForgotPasswordResponseDTO forgotPasswordResponseDTO = securityService.resetPassword(email);

        return created(forgotPasswordResponseDTO, forgotPasswordResponseDTO.getMessage());
    }

    @PostMapping("forgot/password")
    @ResponseStatus(HttpStatus.OK)
    @Hidden
    public void recoverPassword(@Valid @RequestBody RecoverPasswordDTO recoverPasswordDTO) {
        securityService.recoverPassword(recoverPasswordDTO);
    }
}
