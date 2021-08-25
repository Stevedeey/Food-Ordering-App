package com.byteworks.byteworksfoodapp.Payload.Response;

import com.byteworks.byteworksfoodapp.Models.Category;
import com.byteworks.byteworksfoodapp.Models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RestaurantResponse implements Serializable{

    private Long restaurantId;

    private String name;

    private String email;

    private String address;

    private String phoneNumber;

    private Set<Category> availableCategories;


    public static RestaurantResponse build(Restaurant restaurant){
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getEmail(),
                restaurant.getAddress().getAddress(),
                restaurant.getPhoneNumber(),
                restaurant.getAvailableCategories()
        );
    }

}