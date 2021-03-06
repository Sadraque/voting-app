package com.votingapp.repository;

import com.votingapp.domain.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

    Boolean existsByUserId(final String userId);

    List<Vote> findAllByElectionId(final String electionId);
}
