package com.votingapp.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElectionRoutineConfig {

    @Value("${routine.election.open}")
    private Integer openTimeInMinutes;

    @Value("${routine.election.close}")
    private Integer closeTimeInMinutes;
}
