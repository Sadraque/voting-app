package com.votingapp;

import com.votingapp.domain.*;
import com.votingapp.domain.dto.VoteDTO;
import org.assertj.core.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

public class ObjectBuilder {

    public static Vote buildVote() {
        return Vote.builder()
                .id("vote-1")
                .createdAt(LocalDateTime.now())
                .userId(buildUser().getId())
                .electionId(buildElection().getId())
                .candidateId(buildCandidate().getId())
                .build();
    }

    public static VoteDTO buildVoteDTO() {
        VoteDTO voteDTO = new VoteDTO();

        voteDTO.setCandidateId(buildCandidate().getId());
        voteDTO.setElectionId(buildElection().getId());
        voteDTO.setUserId(buildUser().getId());

        return voteDTO;
    }

    public static User buildUser() {
        return User.builder()
                .id("user-1")
                .email("test@test.com")
                .password("test")
                .build();
    }

    public static Election buildElection() {
        return Election.builder()
                .id("election-1")
                .updatedAt(LocalDateTime.now())
                .startAutomatically(true)
                .endAutomatically(true)
                .closed(false)
                .started(false)
                .startDate(DateUtil.now())
                .endDate(DateUtil.tomorrow())
                .createdAt(LocalDateTime.now())
                .name("test")
                .description("election test")
                .candidates(List.of(buildCandidate("candidate-1", "test 1"),
                        buildCandidate("candidate-2", "test 2")))
                .build();
    }

    public static Candidate buildCandidate() {
        return Candidate.builder()
                .id("candidate-1")
                .name("test 1")
                .build();
    }

    public static Candidate buildCandidate(final String id, final String name) {
        return Candidate.builder()
                .id(id)
                .name(name)
                .build();
    }

    public static Audit buildAudit() {
        return Audit.builder()
                .id("audit-1")
                .election(buildElection())
                .createdAt(LocalDateTime.now())
                .candidates(List.of(
                        buildCandidate("candidate-1", "Davis"),
                        buildCandidate("candidate-2", "Joseph"),
                        buildCandidate("candidate-3", "Mary")))
                .build();
    }
}
