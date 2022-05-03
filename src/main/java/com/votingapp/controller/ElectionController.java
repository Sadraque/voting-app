package com.votingapp.controller;

import com.votingapp.domain.Election;
import com.votingapp.domain.dto.ElectionDTO;
import com.votingapp.service.IElectionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/v1/elections",
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class ElectionController implements SecuredController {
    private final IElectionService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Election createElection(@RequestBody @Valid ElectionDTO electionDTO) {
        return service.createElection(electionDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Election> findAllElections(Pageable pageable) {
        return service.findAllElections(pageable);
    }

    @GetMapping(value = "/open/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void startElection(@PathVariable String id) {
        service.openElection(id);
    }

    @GetMapping(value = "/close/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void closeElection(@PathVariable String id) {
        service.closeElection(id);
    }
}
