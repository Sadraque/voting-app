package com.votingapp.repository;

import com.votingapp.domain.abstractions.BaseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<ENTITY extends BaseDocument> extends MongoRepository<ENTITY, String> {

    Optional<ENTITY> findByIdAndDeletedIsFalse(String id);
}
