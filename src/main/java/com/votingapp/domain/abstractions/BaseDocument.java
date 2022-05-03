package com.votingapp.domain.abstractions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.votingapp.domain.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class BaseDocument extends BaseDomain implements Serializable {

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt = LocalDateTime.now();

    @JsonIgnore
    private LocalDateTime deletedAt;

    @JsonIgnore
    private Boolean deleted = Boolean.FALSE;

    @DBRef
    @JsonIgnore
    private User user;

}
