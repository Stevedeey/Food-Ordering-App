package com.byteworks.byteworksfoodapp.Controllers;

import com.byteworks.byteworksfoodapp.Payload.Request.LoginRequest;
import com.byteworks.byteworksfoodapp.Payload.Request.UserRegistrationRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.UserResponse;
import com.byteworks.byteworksfoodapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?>  register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        return new ResponseEntity<>(userService.registration(userRegistrationRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?>  login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?>  getAllUsers() {
        List<UserResponse> allUsers = userService.getAllUsers().stream().map(UserResponse::build).collect(Collectors.toList());
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?>  getUsers(@PathVariable Long userId) {
        return new ResponseEntity<>(UserResponse.build(userService.findUserById(userId)), HttpStatus.OK);
    }
}