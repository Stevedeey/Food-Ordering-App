package com.byteworks.byteworksfoodapp.Payload.Request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserRegistrationRequest {

//    @NotNull(message = "Name cannot be empty")
    private String firstName;

//    @NotNull(message = "last name cannot be empty")
    private String lastName;

    @NotNull(message = "email cannot be empty")
    @Email(message = "must be email")
    private String email;

    private String gender;

    private String dateOfBirth;

    @NotNull(message = "password cannot be empty")
//    @Size(min = 8, max = 20)
    private String password = "password";

    @NotNull(message = "password cannot be empty")
//    @Size(min = 6, max = 24)
    private String confirmPassword = "password";

}
