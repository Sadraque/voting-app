package com.votingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VoteDTO extends RabbitPayloadDTO {

    @NotEmpty
    private String userId;

    @NotEmpty
    private String candidateId;

    @NotEmpty
    private String electionId;
}
