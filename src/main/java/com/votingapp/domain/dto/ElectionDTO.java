package com.votingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectionDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    private Long startDate;

    @NotNull
    private Long endDate;

    @NotNull
    private List<String> candidatesId;
}
