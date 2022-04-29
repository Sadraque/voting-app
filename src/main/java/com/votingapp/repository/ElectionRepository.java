package com.votingapp.repository;

import com.votingapp.domain.Election;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends MongoRepository<Election, String>, ElectionCustomRepository {

}
