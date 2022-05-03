package com.votingapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.votingapp.enumeration.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Document("user")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    private String id;

    @Email
    private String email;

    @NotEmpty
    @JsonIgnore
    private String password;

    private Set<UserRole> roles = new HashSet<>();

    public Collection<UserRole> addUserRole(UserRole userRole) {
        this.roles.add(userRole);
        return this.roles;
    }
}
