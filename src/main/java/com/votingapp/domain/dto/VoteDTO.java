package com.votingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String candidateId;

    @NotEmpty
    private String electionId;
}
