package com.votingapp.controller;

import com.votingapp.domain.Election;
import com.votingapp.domain.dto.ElectionDTO;
import com.votingapp.service.IElectionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/elections",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class ElectionController {
    private final IElectionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Election createElection(@RequestBody @Valid ElectionDTO electionDTO) {
        return service.createElection(electionDTO);
    }
}
