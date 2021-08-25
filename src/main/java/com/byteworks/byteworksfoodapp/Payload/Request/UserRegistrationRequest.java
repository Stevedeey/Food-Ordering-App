package com.byteworks.byteworksfoodapp.Payload.Request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserRegistrationRequest {

    private String firstName;

    private String lastName;

    @NotNull(message = "email cannot be empty")
    @Email(message = "must be email")
    private String email;

    private String gender;

    private String dateOfBirth;

    @NotNull(message = "password cannot be empty")
    private String password = "password";

    @NotNull(message = "password cannot be empty")
    private String confirmPassword = "password";

}
