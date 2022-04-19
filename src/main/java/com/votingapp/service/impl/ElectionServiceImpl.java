package com.votingapp.service.impl;

import com.votingapp.domain.Candidate;
import com.votingapp.domain.Election;
import com.votingapp.domain.dto.ElectionDTO;
import com.votingapp.exception.BadRequestException;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.exception.PreconditionFailedException;
import com.votingapp.repository.CandidateRepository;
import com.votingapp.repository.ElectionRepository;
import com.votingapp.service.IElectionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ElectionServiceImpl implements IElectionService {

    private final ElectionRepository repository;
    private final CandidateRepository candidateRepository;


    @Override
    public Election createElection(final ElectionDTO electionDTO) {

        Date startDate = new Date(electionDTO.getStartDate());
        Date endDate = new Date(electionDTO.getEndDate());

        if (endDate.before(startDate)) {
            throw new BadRequestException("endDate can not be before startDate");
        }

        List<String> candidatesId = electionDTO.getCandidatesId();

        if (candidatesId == null || candidatesId.size() <= 1) {
            throw new BadRequestException("candidatesId must be provided and the list of candidates must be greater than 1");
        }

        List<Candidate> candidates = new ArrayList<>();
        List<String> candidatesIdNotFound = new ArrayList<>();

        candidatesId.forEach(id -> {
            Optional<Candidate> candidate = candidateRepository.findById(id);

            if (candidate.isPresent()) {
                candidates.add(candidate.get());
            } else {
                candidatesIdNotFound.add(id);
            }
        });

        if (!candidatesIdNotFound.isEmpty()) {
            throw new EntityNotFoundException(Candidate.class, candidatesIdNotFound.toString());
        }

        Election election = Election.builder()
                .startDate(startDate)
                .endDate(endDate)
                .updatedAt(LocalDateTime.now())
                .name(electionDTO.getName())
                .description(electionDTO.getDescription())
                .candidates(candidates)
                .started(startDate.before(new Date()) && electionDTO.getStartAutomatically())
                .startAutomatically(electionDTO.getStartAutomatically())
                .build();

        return repository.save(election);
    }

    @Override
    public void openElection(final String electionId) {
        Optional<Election> optionalElection = repository.findById(electionId);

        if (optionalElection.isEmpty()) {
            throw new EntityNotFoundException(Election.class);
        }

        Election election = optionalElection.get();

        if (election.getClosed()) {
            throw new PreconditionFailedException("It is not possible to open a closed Election");
        }

        election.setStarted(true);
        election.setUpdatedAt(LocalDateTime.now());

        repository.save(election);
    }

    @Override
    public void closeElection(final String electionId) {
        Optional<Election> optionalElection = repository.findById(electionId);

        if (optionalElection.isEmpty()) {
            throw new EntityNotFoundException(Election.class);
        }

        Election election = optionalElection.get();

        if (election.getClosed()) {
            throw new PreconditionFailedException("Election already closed");
        }

        election.setClosed(true);
        election.setUpdatedAt(LocalDateTime.now());

        repository.save(election);
    }

    @Override
    public Page<Election> findAllElections(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Election updateElection(final Election election) {
        return repository.save(election);
    }
}
