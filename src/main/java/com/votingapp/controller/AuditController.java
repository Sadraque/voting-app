package com.votingapp.controller;

import com.votingapp.domain.Audit;
import com.votingapp.service.IAuditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/audits",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AuditController {
    private final IAuditService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Audit createAudit() {
        return service.createAudit();
    }
}
