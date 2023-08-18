package com.company.proxyproject.dto.auth;

import com.company.proxyproject.constants.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {
    @NotBlank(message = "user.firstname.not.blank")
    private String firstName;

    @NotBlank(message = "user.lastname.not.blank")
    private String lastName;

    @NotBlank(message = "user.username.not.valid")
    private String username;

    @Email(message = "user.email.not.valid")
    private String email;

    @NotNull(message = "gender.not.null")
    private Gender gender;
}
