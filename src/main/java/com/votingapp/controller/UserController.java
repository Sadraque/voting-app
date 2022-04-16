package com.votingapp.controller;

import com.votingapp.domain.User;
import com.votingapp.exception.EntityNotFoundException;
import com.votingapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    private final UserRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return repository.save(user);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable String id, @RequestBody User user) {
        User entity = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        entity = user;
        entity.setId(id);

        repository.save(entity);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String id) {
        User user = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        repository.delete(user);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUserById(@PathVariable String id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
