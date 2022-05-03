package com.votingapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoverPasswordDTO extends BaseDTO {

    @NotEmpty
    private String token;

    @NotEmpty
    private String newPassword;

}
