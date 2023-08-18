package com.company.proxyproject.dto.auth;

import com.company.proxyproject.constants.enums.Gender;
import com.company.proxyproject.constants.enums.Status;
import com.company.proxyproject.entity.auth.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Optional;

import static com.company.proxyproject.util.DateUtil.formatDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String password;

    private String username;

    private String email;

    private String status;

    private String gender;

    private String registeredDate;


    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.password = user.getPassword();
        this.registeredDate = formatDateTime(user.getCreatedAt());
        this.status = Optional.of(user).map(User::getStatus).map(Status::name).orElse("");
        this.gender = Optional.of(user).map(User::getGender).map(Gender::name).orElse("");
    }

}
