package com.byteworks.byteworksfoodapp.Payload.Request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RestaurantRegistrationRequest {

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "email cannot be empty")
    @Email(message = "must be email")
    private String email;

    private String address;

    private String phoneNumber;

    @NotBlank(message = "city cannot be empty")
    private String city;

    @NotNull(message = "password cannot be empty")
    @Size(min = 8, max = 20)
    private String password = "password";

    @NotNull(message = "password cannot be empty")
    @Size(min = 8, max = 24)
    private String confirmPassword = "password";

}