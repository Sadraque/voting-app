package com.votingapp.controller;

import com.votingapp.domain.Vote;
import com.votingapp.domain.dto.VoteDTO;
import com.votingapp.service.IVoteService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "api/v1/votes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class VoteController {
    private final IVoteService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createVote(@RequestBody @Valid VoteDTO voteDTO) {
        service.createVote(voteDTO, true);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vote findVoteById(@PathVariable String id) {
        return service.findVoteById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Vote> findAllVotes(Pageable pageable) {
        return service.findAllVotes(pageable);
    }
}
