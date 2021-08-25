package com.byteworks.byteworksfoodapp.Controllers;

import com.byteworks.byteworksfoodapp.Payload.Request.LoginRequest;
import com.byteworks.byteworksfoodapp.Payload.Request.RestaurantRegistrationRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.RestaurantResponse;
import com.byteworks.byteworksfoodapp.Services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
@CrossOrigin
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant/register")
    public ResponseEntity<?>  register(@Valid @RequestBody RestaurantRegistrationRequest restaurantRegistrationRequest) {
        return new ResponseEntity<>(restaurantService.registration(restaurantRegistrationRequest), HttpStatus.CREATED);
    }

    @PostMapping("/restaurant/login")
    public ResponseEntity<?>  login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(restaurantService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/restaurant")
    public ResponseEntity<?>  getAllRestaurants() {
        List<RestaurantResponse> allRestaurants = restaurantService.getAllRestaurants().stream().map(RestaurantResponse::build).collect(Collectors.toList());
        return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{Id}")
    public ResponseEntity<?>  getRestaurants(@PathVariable Long Id) {
        return new ResponseEntity<>(RestaurantResponse.build(restaurantService.findRestaurantById(Id)), HttpStatus.OK);
    }
}