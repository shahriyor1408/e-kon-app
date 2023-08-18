package com.company.proxyproject.dto.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    @NotBlank(message = "auth.usernameoremail.not.valid")
    private String usernameOrEmail;

    @NotBlank(message = "auth.password.not.valid")
    private String password;
}
