package com.votingapp.service.impl;

import com.votingapp.domain.Candidate;
import com.votingapp.domain.Election;
import com.votingapp.domain.User;
import com.votingapp.domain.Vote;
import com.votingapp.domain.dto.VoteDTO;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.exception.InvalidVoteException;
import com.votingapp.repository.CandidateRepository;
import com.votingapp.repository.ElectionRepository;
import com.votingapp.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;
    private final ElectionRepository electionRepository;

    @Override
    @Transactional
    public Vote createVote(final VoteDTO voteDTO) {
        final Election election = electionRepository.findById(voteDTO.getElectionId())
                .orElseThrow(() -> new EntityNotFoundException(Election.class));

        if (!election.getStarted() || election.getClosed()) {
            throw new InvalidVoteException("Election it is closed");
        }

        final User user = userRepository.findById(voteDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        final Candidate candidate = candidateRepository.findById(voteDTO.getCandidateId())
                .orElseThrow(() -> new EntityNotFoundException(Candidate.class));

         if (repository.existsByUser(user)) {
            throw new InvalidVoteException("User already voted");
        }

        Vote vote = Vote.builder()
                .user(user)
                .candidate(candidate)
                .election(election)
                .build();

        return repository.save(vote);
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
