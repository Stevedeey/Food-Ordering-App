package com.byteworks.byteworksfoodapp.Services;


import com.byteworks.byteworksfoodapp.Models.Restaurant;
import com.byteworks.byteworksfoodapp.Payload.Request.LoginRequest;
import com.byteworks.byteworksfoodapp.Payload.Request.RestaurantRegistrationRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.LoginResponse;
import com.byteworks.byteworksfoodapp.Payload.Response.RestaurantResponse;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    void saveRestaurant(Restaurant restaurant);
    boolean existsByMail(String email);
    Optional<Restaurant> getRestaurantByEmail(String email);
    Restaurant findRestaurantByEmail(String email);
    List<Restaurant> getAllRestaurants();
    Restaurant findRestaurantById(Long userId);
    RestaurantResponse registration(RestaurantRegistrationRequest restaurantRegistrationRequest);
    LoginResponse login(LoginRequest loginRequest);
}