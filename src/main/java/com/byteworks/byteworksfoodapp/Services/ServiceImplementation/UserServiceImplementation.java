package com.byteworks.byteworksfoodapp.Services.ServiceImplementation;

import com.byteworks.byteworksfoodapp.Exceptions.BadRequestException;
import com.byteworks.byteworksfoodapp.Exceptions.ResourceNotFoundException;
import com.byteworks.byteworksfoodapp.Models.Enums.ERole;
import com.byteworks.byteworksfoodapp.Models.User;
import com.byteworks.byteworksfoodapp.Payload.Request.LoginRequest;
import com.byteworks.byteworksfoodapp.Payload.Request.UserRegistrationRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.LoginResponse;
import com.byteworks.byteworksfoodapp.Payload.Response.UserResponse;
import com.byteworks.byteworksfoodapp.Repositories.RoleRepository;
import com.byteworks.byteworksfoodapp.Repositories.UserRepository;
import com.byteworks.byteworksfoodapp.Security.Service.UserDetailService;
import com.byteworks.byteworksfoodapp.Services.UserService;
import com.byteworks.byteworksfoodapp.Utils.JWTUtils;
import com.byteworks.byteworksfoodapp.Utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"Users"})
public class UserServiceImplementation implements UserService {


    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;
    private final JWTUtils jwtUtil;
    private final Utility utility;


    @Override
    public UserResponse registration(UserRegistrationRequest userRegistrationRequest) {

        userRegistrationRequest.setPassword(userRegistrationRequest.getEmail());
        userRegistrationRequest.setConfirmPassword(userRegistrationRequest.getEmail());

        if(existsByMail(userRegistrationRequest.getEmail())){
            throw new BadRequestException("Error: Email is already taken!");
        }
        if(!(userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword()))){
            throw new BadRequestException("Error: Password does not match");
        }
        if(!isValidPassword(userRegistrationRequest.getPassword())){
            throw new BadRequestException("Error: Password must be between 8 and 40 long and must be an Alphabet or a Number");
        }
        if(!isValidEmail(userRegistrationRequest.getEmail())){
            throw new BadRequestException("Error: Email must be valid");
        }

        User user = new User(
                userRegistrationRequest.getFirstName(),
                userRegistrationRequest.getLastName(),
                userRegistrationRequest.getEmail(),
                userRegistrationRequest.getGender(),
                userRegistrationRequest.getDateOfBirth(),
                bCryptPasswordEncoder.encode(userRegistrationRequest.getPassword()));

        user.setRoles(utility.assignRole(ERole.USER));
        saveUser(user);
        return UserResponse.build(user);
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        if(!existsByMail(loginRequest.getEmail())){
            UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
            userRegistrationRequest.setEmail(loginRequest.getEmail());
            registration(userRegistrationRequest);
        }

        loginRequest.setPassword(loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailService.loadUserByUsername(loginRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);
        return new LoginResponse(jwtToken);
    }


    private boolean isValidPassword(String password) {
        String regex = "^(([0-9]|[a-z]|[A-Z]|[@.&*!#$%^():;'?><])*){8,40}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (password == null) {
            throw new BadRequestException("Error: Password cannot be null");
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }


    private boolean isValidEmail(String email) {
        String regex = "^(.+)@(\\w+)\\.(\\w+)$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        if (email == null) {
            throw new BadRequestException("Error: Email cannot be null");
        }
        Matcher m = p.matcher(email);
        return m.matches();
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public boolean existsByMail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new ResourceNotFoundException("Incorrect parameter; email " + email + " does not exist");
        return user.get();
    }


    @Override
    @Cacheable(value = "Users")
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()) throw new ResourceNotFoundException("There are no registered users yet");
        return users;
    }


    @Override
    @Cacheable(key = "#userId")
    public User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) throw new ResourceNotFoundException("Incorrect parameter; userId " + userId + " does not exist");
        return user.get();

    }

}
