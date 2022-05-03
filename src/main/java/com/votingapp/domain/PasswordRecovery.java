package com.votingapp.domain;

import com.votingapp.domain.abstractions.BaseDocument;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@Document("password_recovery")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PasswordRecovery extends BaseDocument {

    @Id
    private Long id;

    @Email
    private String email;

    private String token;

    @Transient
    private Integer expirationInHour;

}
