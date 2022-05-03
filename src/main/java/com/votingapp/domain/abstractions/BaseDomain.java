package com.votingapp.domain.abstractions;

import lombok.Data;

@Data
public abstract class BaseDomain {
    public abstract Long getId();
}
