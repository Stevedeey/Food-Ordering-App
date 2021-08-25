package com.byteworks.byteworksfoodapp.Services;


import com.byteworks.byteworksfoodapp.Models.User;
import com.byteworks.byteworksfoodapp.Payload.Request.LoginRequest;
import com.byteworks.byteworksfoodapp.Payload.Request.UserRegistrationRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.LoginResponse;
import com.byteworks.byteworksfoodapp.Payload.Response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    boolean existsByMail(String email);

    Optional<User> getUserByEmail(String email);

    User findUserByEmail(String email);

    List<User> getAllUsers();

    User findUserById(Long userId);

    UserResponse registration(UserRegistrationRequest userRegistrationRequest);

    LoginResponse login(LoginRequest loginRequest);
}