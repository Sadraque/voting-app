package com.votingapp.service.impl;

import com.votingapp.domain.Audit;
import com.votingapp.domain.Candidate;
import com.votingapp.domain.Election;
import com.votingapp.domain.Vote;
import com.votingapp.domain.dto.PercentageDTO;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.repository.AuditRepository;
import com.votingapp.repository.ElectionRepository;
import com.votingapp.repository.VoteRepository;
import com.votingapp.service.IAuditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@AllArgsConstructor
public class AuditServiceImpl implements IAuditService {

    private final AuditRepository repository;
    private final VoteRepository voteRepository;
    private final ElectionRepository electionRepository;

    @Override
    @Transactional
    public Audit createAudit(final String electionId) {
        final Optional<Election> optionalElection = electionRepository.findById(electionId);

        if (optionalElection.isEmpty()) {
            throw new EntityNotFoundException(Election.class);
        }

        final Election election = optionalElection.get();

        if (election.getClosed()) {
            Optional<Audit> optionalAudit = findLastAuditByElectionId(electionId);

            if (optionalAudit.isPresent()) {
                return optionalAudit.get();
            }
        }

        final List<Candidate> candidates = election.getCandidates();

        final List<Vote> votes = voteRepository.findAllByElectionId(electionId);

        final long totalOfVotes = votes.size();

        List<PercentageDTO> percentages = new ArrayList<>();

        candidates.forEach(candidate -> percentages.add(new PercentageDTO(candidate,
                BigDecimal.valueOf(votes.stream()
                        .filter(vote -> vote.getCandidate().getId().equals(candidate.getId()))
                        .count() * 100.00D / totalOfVotes)
                        .setScale(2, RoundingMode.UP))));

        final Audit audit = Audit.builder()
                .candidates(candidates)
                .percentages(percentages)
                .totalOfVotes(totalOfVotes)
                .election(election)
                .build();

        repository.save(audit);

        return audit;
    }

    @Override
    public Audit findAuditById(final String auditId) {
        return repository.findById(auditId)
                .orElseThrow(() -> new EntityNotFoundException(Audit.class));
    }

    @Override
    public Optional<Audit> findLastAuditByElectionId(final String electionId) {
        return repository.findTopByElectionIdOrderByCreatedAtDesc(electionId);
    }

}
