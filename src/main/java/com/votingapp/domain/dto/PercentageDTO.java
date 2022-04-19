package com.votingapp.domain.dto;

import com.votingapp.domain.Candidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PercentageDTO {

    private Candidate candidate;
    private BigDecimal percentage;

}
