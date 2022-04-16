package com.votingapp.repository;

import com.votingapp.domain.User;
import com.votingapp.domain.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    Boolean existsByUser(User user);
}
