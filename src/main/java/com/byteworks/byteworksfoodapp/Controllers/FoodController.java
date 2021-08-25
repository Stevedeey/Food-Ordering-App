package com.byteworks.byteworksfoodapp.Controllers;

import com.byteworks.byteworksfoodapp.Models.Food;
import com.byteworks.byteworksfoodapp.Models.FoodPage;
import com.byteworks.byteworksfoodapp.Payload.Request.FoodRequest;
import com.byteworks.byteworksfoodapp.Payload.Response.FoodResponse;
import com.byteworks.byteworksfoodapp.Services.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/foods")
@CrossOrigin
public class FoodController {

    private final ModelMapper modelMapper;
    private final FoodService foodService;

    @Autowired
    public FoodController(ModelMapper modelMapper, FoodService foodService) {
        this.modelMapper = modelMapper;
        this.foodService = foodService;
    }


    @GetMapping
    public ResponseEntity<Page<FoodResponse>> getAllFoods(FoodPage foodPage) {
        Page<Food> foods = foodService.getAllFoods(foodPage);
        List<FoodResponse> foodResponseList = getFoodDTOList(foods);
        Page<FoodResponse> foodDTOPage = new PageImpl<>(foodResponseList);
        return new ResponseEntity<>(foodDTOPage, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<FoodResponse>> searchFoods(@Param("keyword") String keyword) {
        keyword = Objects.requireNonNullElse(keyword, "");
        log.info(keyword + ": This is the keyword being searched");
        Page<Food> foods = foodService.searchAllFoods(keyword.toLowerCase());
        List<FoodResponse> foodResponseList = getFoodDTOList(foods);
        Page<FoodResponse> foodDTOPage = new PageImpl<>(foodResponseList);
        return new ResponseEntity<>(foodDTOPage, HttpStatus.OK);
    }

    @GetMapping("restaurant/{restaurantId}")
    public ResponseEntity<Page<FoodResponse>> getFoodsByRestaurant(@PathVariable Long restaurantId, FoodPage foodPage) {
        Page<Food> foods = foodService.getFoodsByRestaurant(foodPage, restaurantId);
        List<FoodResponse> foodResponseList = getFoodDTOList(foods);
        Page<FoodResponse> foodDTOPage = new PageImpl<>(foodResponseList);
        return new ResponseEntity<>(foodDTOPage, HttpStatus.OK);
    }

    @GetMapping("restaurant/{restaurantId}/category")
    public ResponseEntity<Page<FoodResponse>> getFoodsByRestaurantAndCategory
            (@PathVariable Long restaurantId, @Param("categoryName") String categoryName, FoodPage foodPage) {
        Page<Food> foods = foodService.getFoodsByRestaurantAndCategory(foodPage, categoryName, restaurantId);
        List<FoodResponse> foodResponseList = getFoodDTOList(foods);
        Page<FoodResponse> foodDTOPage = new PageImpl<>(foodResponseList);
        return new ResponseEntity<>(foodDTOPage, HttpStatus.OK);
    }

    @GetMapping("categories/{category}")
    public ResponseEntity<Page<FoodResponse>> getFoodsByCategory(@PathVariable String category, FoodPage foodPage) {
        Page<Food> foods = foodService.getFoodsByCategory(foodPage, category);
        List<FoodResponse> foodResponseList = getFoodDTOList(foods);
        Page<FoodResponse> foodDTOPage = new PageImpl<>(foodResponseList);
        return new ResponseEntity<>(foodDTOPage, HttpStatus.OK);
    }


    @GetMapping("/{foodId}")
    public ResponseEntity<?> getFoodById(@PathVariable Long foodId){
        return new ResponseEntity<>(foodService.findFoodById(foodId), HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-food")
//    @Secured("ADMIN")
    public ResponseEntity<?> createFood(@Valid @RequestBody FoodRequest foodRequest){
        foodService.createFood(foodRequest);
        return new ResponseEntity<>("Food successfully created", HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update-food/{id}")
//    @Secured("ADMIN")
    public ResponseEntity<?> updateFood(@PathVariable("id") long foodId, @Valid @RequestBody FoodRequest foodRequest){
        foodService.updateFood(foodId, foodRequest);
        return new ResponseEntity<>("Food successfully updated", HttpStatus.ACCEPTED);
    }

    private List<FoodResponse> getFoodDTOList(Page<Food> foods) {
        return foods.stream().map(food -> modelMapper
                .map(food, FoodResponse.class))
                .collect(Collectors.toList());
    }


}
