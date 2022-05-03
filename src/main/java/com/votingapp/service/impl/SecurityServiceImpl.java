package com.votingapp.service.impl;

import com.votingapp.configuration.PasswordRecoveryConfig;
import com.votingapp.domain.PasswordRecovery;
import com.votingapp.domain.User;
import com.votingapp.domain.UserDetailsSecurity;
import com.votingapp.domain.dto.ForgotPasswordResponseDTO;
import com.votingapp.domain.dto.RecoverPasswordDTO;
import com.votingapp.enumeration.EmailLayout;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.exception.PasswordRecoveryRequestException;
import com.votingapp.exception.UnauthenticatedException;
import com.votingapp.repository.PasswordRecoveryRepository;
import com.votingapp.repository.UserRepository;
import com.votingapp.service.IEmailService;
import com.votingapp.service.IEncryptService;
import com.votingapp.service.ISecurityService;
import com.votingapp.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements ISecurityService {

    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserRepository userRepository;
    private final PasswordRecoveryConfig passwordRecoveryConfig;
    private final IEncryptService encryptService;
    private final JWTUtils jwtUtils;
    private final IEmailService emailService;

    @Override
    @Transactional
    public ForgotPasswordResponseDTO resetPassword(String email) {
        if (StringUtils.isBlank(email)) {
            throw new BadCredentialsException("Invalid recovery password solicitation");
        }

        PasswordRecovery passwordRecovery = PasswordRecovery.builder()
                .email(email)
                .expirationInHour(passwordRecoveryConfig.getExpirationInHour())
                .token(generatePasswordRecoveryToken())
                .build();

        passwordRecovery = passwordRecoveryRepository.save(passwordRecovery);

        ForgotPasswordResponseDTO forgotPasswordResponseDTO = new ForgotPasswordResponseDTO();
        forgotPasswordResponseDTO.setMessage("Request to change password sent to the email: " +email);
        forgotPasswordResponseDTO.setValidUntil(String.format("%d hour", passwordRecoveryConfig.getExpirationInHour()));

        emailService.send(email, "Reset Password", EmailLayout.PASSWORD_RECOVERY, passwordRecovery);

        return forgotPasswordResponseDTO;
    }

    @Override
    @Transactional
    public void recoverPassword(RecoverPasswordDTO recoverPasswordDTO) {
        if (StringUtils.isBlank(recoverPasswordDTO.getToken()) || StringUtils.isBlank(recoverPasswordDTO.getNewPassword())) {
            throw new PasswordRecoveryRequestException();
        }

        Optional<PasswordRecovery> passwordRecovery = passwordRecoveryRepository.findByTokenAndDeletedIsFalse(recoverPasswordDTO.getToken());

        if (passwordRecovery.isEmpty()) {
            throw new PasswordRecoveryRequestException("Password recovery request not found");
        }

        PasswordRecovery passwordRecoveryEntity = passwordRecovery.get();

        if (isPasswordRecoveryExpired(passwordRecoveryEntity.getCreatedAt())) {
            throw new PasswordRecoveryRequestException("Password recovery request expired");
        }

        Optional<User> user = userRepository.findByEmail(passwordRecoveryEntity.getEmail());

        if (user.isEmpty()) {
            throw new EntityNotFoundException(User.class);
        }

        User userEntity = user.get();
        userEntity.setPassword(encryptService.encrypt(recoverPasswordDTO.getNewPassword()));

        userRepository.save(userEntity);

        passwordRecoveryEntity.setDeleted(Boolean.TRUE);
        passwordRecoveryEntity.setDeletedAt(LocalDateTime.now());

        passwordRecoveryRepository.save(passwordRecoveryEntity);
    }

    @Override
    public String generatePasswordRecoveryToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Boolean isPasswordRecoveryExpired(LocalDateTime date) {
        return date == null
                || DateUtils.addHours(
                        Date.from(date.atZone(ZoneId.systemDefault()).toInstant()),
                        passwordRecoveryConfig.getExpirationInHour()).before(new Date());
    }

    @Override
    public User getLoggedUser() {
        try {

            UserDetailsSecurity userDetailsSecurity = (UserDetailsSecurity) SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getPrincipal();

            if (userDetailsSecurity == null) {
                return null;
            }

            return userRepository.findById(userDetailsSecurity.getId())
                    .orElse(null);

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String refreshToken() {
        User user = getLoggedUser();

        if (user == null) {
            throw new UnauthenticatedException();
        }

        return jwtUtils.generateToken(user.getEmail());
    }
}
