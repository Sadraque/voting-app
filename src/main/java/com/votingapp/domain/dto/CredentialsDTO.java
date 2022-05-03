package com.votingapp.domain.dto;

import com.votingapp.domain.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialsDTO extends BaseDTO implements Serializable {
    private String email;
    private String password;
}
