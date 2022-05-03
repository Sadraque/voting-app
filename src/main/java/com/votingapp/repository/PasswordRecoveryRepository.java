package com.votingapp.repository;

import com.votingapp.domain.PasswordRecovery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRecoveryRepository extends BaseRepository<PasswordRecovery> {
    Optional<PasswordRecovery> findByEmailAndDeletedIsFalse(String email);

    Optional<PasswordRecovery> findByTokenAndDeletedIsFalse(String token);

    Boolean existsByEmailAndDeletedIsFalse(String email);

    Boolean existsByTokenAndDeletedIsFalse(String token);
}
