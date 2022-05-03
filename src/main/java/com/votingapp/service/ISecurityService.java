package com.votingapp.service;

import com.votingapp.domain.User;
import com.votingapp.domain.dto.ForgotPasswordResponseDTO;
import com.votingapp.domain.dto.RecoverPasswordDTO;
import java.time.LocalDateTime;

public interface ISecurityService {

    ForgotPasswordResponseDTO resetPassword(final String email);


    void recoverPassword(final RecoverPasswordDTO recoverPasswordDTO);


    String generatePasswordRecoveryToken();


    Boolean isPasswordRecoveryExpired(LocalDateTime date);


    User getLoggedUser();


    String refreshToken();
}
