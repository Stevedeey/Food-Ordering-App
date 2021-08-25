package com.byteworks.byteworksfoodapp.Payload.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private String message;
    private int status_code;
}
