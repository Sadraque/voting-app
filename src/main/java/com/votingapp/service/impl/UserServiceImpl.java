package com.votingapp.service.impl;

import com.votingapp.domain.User;
import com.votingapp.domain.dto.UserSaveDTO;
import com.votingapp.enumeration.UserRole;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.exception.PreconditionFailedException;
import com.votingapp.repository.UserRepository;
import com.votingapp.service.IEncryptService;
import com.votingapp.service.IUserService;
import com.votingapp.utils.TypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IEncryptService encryptService;
    private final UserRepository userRepository;

    @Override
    public User save(final UserSaveDTO userSaveDTO) {
        if (existsByEmail(userSaveDTO.getEmail())) {
            throw new PreconditionFailedException("User with this email already registered");
        }

        User user = TypeUtils.parseToEntity(userSaveDTO, User.class);

        user.setId(null);
        user.addUserRole(UserRole.CLIENT);
        user.setPassword(encryptService.encrypt(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public Boolean existsByEmail(final String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findUserById(final String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));
    }

    @Override
    public void deleteUser(final String id) {
        userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        userRepository.deleteById(id);
    }

}
