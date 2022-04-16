package com.votingapp.service;

import com.votingapp.domain.Vote;
import com.votingapp.domain.dto.VoteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface IVoteService {

    Vote createVote(final VoteDTO voteDTO);

    Vote findVoteById(final String id);

    Page<Vote> findAllVotes(final Pageable pageable);
}
