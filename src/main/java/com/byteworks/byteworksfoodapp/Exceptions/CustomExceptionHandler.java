package com.byteworks.byteworksfoodapp.Exceptions;

import com.byteworks.byteworksfoodapp.Payload.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(prepareErrorResponse(status, exception), status);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(prepareErrorResponse(status, exception), status);
    }


    public static Response prepareErrorResponse(HttpStatus status, Exception ex) {
        Response response = new Response();
        try {
            response.setMessage(ex.getMessage());
            response.setStatus_code(status.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
