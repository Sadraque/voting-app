package com.votingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class VotingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(VotingAppApplication.class, args);
    }

}
