package com.votingapp.controller;

import com.votingapp.domain.User;
import com.votingapp.domain.dto.UserSaveDTO;
import com.votingapp.service.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController extends BaseController {

    private final IUserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserSaveDTO userSaveDTO) {
        return userService.save(userSaveDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Hidden
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @Hidden
    public User findUserById(@PathVariable String id) {
        return userService.findUserById(id);
    }
}
