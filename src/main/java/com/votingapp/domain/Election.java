package com.votingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Document("election")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Election {

    @Id
    private String id;

    private String name;

    private String description;

    @DBRef
    private Audit audit;

    @DBRef
    private List<Candidate> candidates;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private Date startDate;

    private Date endDate;

    @Builder.Default
    private Boolean started = Boolean.FALSE;

    @Builder.Default
    private Boolean closed = Boolean.FALSE;

    @Builder.Default
    private Boolean startAutomatically = Boolean.TRUE;
}
