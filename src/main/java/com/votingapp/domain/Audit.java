package com.votingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.votingapp.domain.dto.PercentageDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("audit")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Audit {

    @Id
    private String id;

/*    @DBRef
    private List<Vote> votes;*/

    @DBRef
    private List<Candidate> candidates;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private List<PercentageDTO> percentages;

    private Long totalOfVotes;

    @DBRef
    private Election election;

}
