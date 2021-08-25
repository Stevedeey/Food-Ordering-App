package com.byteworks.byteworksfoodapp.Services.ServiceImplementation;

import com.byteworks.byteworksfoodapp.Exceptions.BadRequestException;
import com.byteworks.byteworksfoodapp.Exceptions.ResourceNotFoundException;
import com.byteworks.byteworksfoodapp.Models.Address;
import com.byteworks.byteworksfoodapp.Models.Enums.City;
import com.byteworks.byteworksfoodapp.Models.Enums.ERole;
import com.byteworks.byteworksfoodapp.Models.Restaurant;
import com.byteworks.byteworksfoodapp.Payload.Request.LoginRequest;
import com.byteworks.byteworksfoodapp.Payload.Request.RestaurantRegistrationRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.LoginResponse;
import com.byteworks.byteworksfoodapp.Payload.Response.RestaurantResponse;
import com.byteworks.byteworksfoodapp.Repositories.AddressRepository;
import com.byteworks.byteworksfoodapp.Repositories.RestaurantRepository;
import com.byteworks.byteworksfoodapp.Repositories.RoleRepository;
import com.byteworks.byteworksfoodapp.Security.Service.UserDetailService;
import com.byteworks.byteworksfoodapp.Services.RestaurantService;
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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"Restaurant"})
public class RestaurantServiceImplementation implements RestaurantService {


    private final UserDetailService userDetailService;
    private final AddressRepository addressRepository;
    private final RestaurantRepository restaurantRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtil;
    private final Utility utility;


    @Override
    public RestaurantResponse registration(RestaurantRegistrationRequest restaurantRegistrationRequest){

        if(existsByMail(restaurantRegistrationRequest.getEmail())) {
            throw new BadRequestException("Error: Email is already taken!");
        }
        if(!(restaurantRegistrationRequest.getPassword().equals(restaurantRegistrationRequest.getConfirmPassword()))){
            throw new BadRequestException("Error: Password does not match");
        }
        if(!isValidPassword(restaurantRegistrationRequest.getPassword())){
            throw new BadRequestException("Error: Password must be " +
                    "between 8 and 40 long and must be an Alphabet or a Number");
        }
        if(!isValidEmail(restaurantRegistrationRequest.getEmail())){
            throw new BadRequestException("Error: Email must be valid");
        }

        Set<String> cities = new HashSet<>(List.of("ABUJA",
                "LAGOS", "IBADAN",
                "UYO", "PORT_HARCOURT",
                "ENUGU", "ASABA",
                "KANO", "UMUAHIA",
                "ONITSHA", "ABA", "OWERRI"));

        City requestCity = null;

        if(!cities.contains(restaurantRegistrationRequest.getCity().toUpperCase()))
            throw new BadRequestException("Invalid City");
        else{

             requestCity = City.valueOf(restaurantRegistrationRequest.getCity());
        }

        Restaurant restaurant = new Restaurant(
                restaurantRegistrationRequest.getName(),
                restaurantRegistrationRequest.getEmail(),
                restaurantRegistrationRequest.getPhoneNumber(),
                addressRepository.save(new Address(restaurantRegistrationRequest.getAddress(), requestCity)),
                bCryptPasswordEncoder.encode(restaurantRegistrationRequest.getPassword()));

        restaurant.setRoles(utility.assignRole(ERole.RESTAURANT));

        saveRestaurant(restaurant);

        return RestaurantResponse.build(restaurant);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
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
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    @Override
    public boolean existsByMail(String email) {
        return restaurantRepository.existsByEmail(email);
    }

    @Override
    public Optional<Restaurant> getRestaurantByEmail(String email) {
        return restaurantRepository.findByEmail(email);
    }


    @Override
    public Restaurant findRestaurantByEmail(String email) {
        Optional<Restaurant> restaurant = restaurantRepository.findByEmail(email);
        if(restaurant.isEmpty()) throw
                new ResourceNotFoundException("Incorrect parameter; email " + email + " does not exist");
        return restaurant.get();
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        if(restaurants.isEmpty()) throw new ResourceNotFoundException("There are no registered restaurants  yet");
        return restaurants;
    }


    @Override
    @Cacheable(key = "#restaurantId")
    public Restaurant findRestaurantById(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if(restaurant.isEmpty()) throw
                new ResourceNotFoundException("Incorrect parameter: restaurantId " + restaurantId + " does not exist");
        return restaurant.get();
    }

}