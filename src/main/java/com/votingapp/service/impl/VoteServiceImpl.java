package com.votingapp.service.impl;

import com.votingapp.domain.Vote;
import com.votingapp.domain.dto.VoteDTO;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.producer.IVoteProducer;
import com.votingapp.repository.VoteRepository;
import com.votingapp.service.IVoteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements IVoteService {

    private final VoteRepository repository;
    private final IVoteProducer producer;


    @Override
    @Transactional
    public Vote createVote(final VoteDTO voteDTO) {
        return repository.save(new Vote(voteDTO));
    }

    @Override
    public void createVote(VoteDTO voteDTO, boolean enqueue) {
        if (enqueue) {
            producer.sendToQueue(voteDTO);
        } else {
            createVote(voteDTO);
        }
    }

    @Override
    public Vote findVoteById(final String id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Vote.class));
    }

    @Override
    public Page<Vote> findAllVotes(final Pageable pageable) {
        return repository.findAll(pageable);
    }
}
