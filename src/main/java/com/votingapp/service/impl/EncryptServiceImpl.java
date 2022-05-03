package com.votingapp.service.impl;

import com.votingapp.service.IEncryptService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptServiceImpl implements IEncryptService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncryptServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String encrypt(String value) {
        if (value == null || value.isBlank()) {
            throw new BadCredentialsException("Invalid value");
        }

        return bCryptPasswordEncoder.encode(value);
    }
}
