package com.votingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.votingapp.domain.dto.VoteDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("vote")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vote {

    @Id
    private String id;

    private String userId;
    private String candidateId;
    private String electionId;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public Vote (VoteDTO voteDTO) {
        this.candidateId = voteDTO.getCandidateId();
        this.electionId = voteDTO.getElectionId();
        this.userId = voteDTO.getUserId();
    }
}
