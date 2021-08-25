package com.byteworks.byteworksfoodapp.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse implements Serializable {

    private String message;
    private String access_token;

    public LoginResponse(String token) {
        this.message = "Welcome, you are logged in";
        this.access_token = token;
    }
}