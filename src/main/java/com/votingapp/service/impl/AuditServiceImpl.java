package com.votingapp.service.impl;

import com.votingapp.domain.Audit;
import com.votingapp.domain.Candidate;
import com.votingapp.domain.Vote;
import com.votingapp.domain.dto.PercentageDTO;
import com.votingapp.repository.AuditRepository;
import com.votingapp.repository.CandidateRepository;
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
    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public Audit createAudit() {
        final List<Candidate> candidates = candidateRepository.findAll();

        final List<Vote> votes = voteRepository.findAll();

        final long totalOfVotes = votes.size();

        Map<Candidate, Long> votesOfCandidates = new HashMap<>();

        List<PercentageDTO> percentages = new ArrayList<>();

        candidates.forEach(candidate -> votesOfCandidates.put(candidate,
                votes.stream()
                        .filter(vote -> vote.getCandidate().getId().equals(candidate.getId()))
                        .count()));

        candidates.forEach(candidate -> percentages.add(new PercentageDTO(candidate,
                BigDecimal.valueOf(votes.stream()
                        .filter(vote -> vote.getCandidate().getId().equals(candidate.getId()))
                        .count() * 100.00D / totalOfVotes)
                        .setScale(2, RoundingMode.UP))));

        final Audit audit = Audit.builder()
                .votes(votes)
                .candidates(candidates)
                .percentages(percentages)
                .totalOfVotes(totalOfVotes)
                .build();

        repository.save(audit);

        return audit;
    }

}
