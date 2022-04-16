package com.votingapp.service.impl;

import com.votingapp.domain.Candidate;
import com.votingapp.domain.Election;
import com.votingapp.domain.dto.ElectionDTO;
import com.votingapp.exception.BadRequestException;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.repository.CandidateRepository;
import com.votingapp.repository.ElectionRepository;
import com.votingapp.service.IElectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
                .name(electionDTO.getName())
                .description(electionDTO.getDescription())
                .candidates(candidates)
                .started(startDate.before(new Date()))
                .build();

        return repository.save(election);
    }

    @Override
    public Election startElection() {
        return null;
    }

    @Override
    public Election closeElection() {
        return null;
    }
}
