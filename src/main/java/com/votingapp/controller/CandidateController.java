package com.votingapp.controller;

import com.votingapp.domain.Candidate;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.repository.CandidateRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/candidates",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CandidateController implements SecuredController {

    private final CandidateRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return repository.save(candidate);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCandidate(@PathVariable String id, @RequestBody Candidate candidate) {
        Candidate entity = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        entity = candidate;
        entity.setId(id);

        repository.save(entity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCandidate(@PathVariable String id) {
        Candidate candidate = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        repository.delete(candidate);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Candidate findCandidateById(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Candidate> findCandidateById(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
