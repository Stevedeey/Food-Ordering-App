package com.byteworks.byteworksfoodapp.Services;

import com.byteworks.byteworksfoodapp.Models.Food;
import com.byteworks.byteworksfoodapp.Models.FoodPage;
import com.byteworks.byteworksfoodapp.Payload.Request.FoodRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FoodService {

    void saveFood(Food food);

    boolean existsById(Long Id);

    Food getFoodByName(String Name);

    Optional<Food> findFoodByName(String Name);

    List<Food> getAllFoods();

    Food findFoodById(Long FoodId);

    void deleteFood(Long FoodId);

    void createFood(FoodRequest FoodRequest);

    void updateFood(Long FoodId, FoodRequest FoodRequest);

    Page<Food> getAllFoods(FoodPage foodPage);

    Page<Food> searchAllFoods(String keyword);

    Page<Food> getFoodsByCategory(FoodPage FoodPage, String categoryName);

    Page<Food> getFoodsByRestaurant(FoodPage foodPage, Long restaurantId);

    Page<Food> getFoodsByRestaurantAndCategory(FoodPage foodPage, String categoryName, Long restaurantId);
}