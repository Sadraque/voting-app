package com.votingapp.service;

import com.votingapp.domain.User;
import com.votingapp.domain.dto.UserSaveDTO;

public interface IUserService {

    User save(final UserSaveDTO userSaveDTO);

    Boolean existsByEmail(final String email);

    User findUserById(final String id);

    void deleteUser(final String id);
}
