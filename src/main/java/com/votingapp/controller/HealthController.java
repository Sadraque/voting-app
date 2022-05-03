package com.votingapp.controller;

import com.votingapp.configuration.RabbitConfig;
import com.votingapp.utils.WebClientUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "actuator",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class HealthController {

    private final WebClientUtils webClientUtils;
    private final RabbitConfig rabbitConfig;

    @GetMapping("rabbitmq/health")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> rabbitmqHealthCheck() {
        return webClientUtils.getWithBasicAuth(rabbitConfig.HOST,
                URI.create(rabbitConfig.HEALTH),
                rabbitConfig.USER,
                rabbitConfig.PASSWORD,
                String.class);
    }
}
