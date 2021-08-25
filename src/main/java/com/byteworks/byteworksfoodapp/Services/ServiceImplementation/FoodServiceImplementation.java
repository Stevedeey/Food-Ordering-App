package com.byteworks.byteworksfoodapp.Services.ServiceImplementation;

import com.byteworks.byteworksfoodapp.Exceptions.BadRequestException;
import com.byteworks.byteworksfoodapp.Models.*;
import com.byteworks.byteworksfoodapp.Models.Enums.CategoryEnum;
import com.byteworks.byteworksfoodapp.Payload.Request.FoodRequest;
import com.byteworks.byteworksfoodapp.Repositories.CategoryRepository;
import com.byteworks.byteworksfoodapp.Repositories.FoodRepository;
import com.byteworks.byteworksfoodapp.Repositories.RestaurantRepository;
import com.byteworks.byteworksfoodapp.Services.FoodService;
import com.byteworks.byteworksfoodapp.Utils.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"Food"})
public class FoodServiceImplementation implements FoodService {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;


    @Override
    public void saveFood(Food food) {
        foodRepository.save(food);
    }

    @Override
    public boolean existsById(Long Id) {
        return foodRepository.existsById(Id);
    }

    @Override
    public Food getFoodByName(String name) {
        return foodRepository.getByName(name);
    }

    @Override
    public Optional<Food> findFoodByName(String name) {
        return foodRepository.findByName(name);
    }

    @Override
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    public Food findFoodById(Long FoodId) {
        return foodRepository.getById(FoodId);
    }

    @Override
    public void deleteFood(Long FoodId) {
        foodRepository.deleteById(FoodId);
    }

    @Override
    public void createFood(FoodRequest foodRequest) {
        Food food = new Food();
        setFoodDetail(foodRequest, food);
        saveFood(food);
    }

    @Override
    public void updateFood(Long foodId, FoodRequest foodRequest) {
        Food food = findFoodById(foodId);
        setFoodDetail(foodRequest, food);
        saveFood(food);
    }

    private void setFoodDetail(FoodRequest foodRequest, Food food) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Restaurant loggedRestaurant = restaurantRepository.findByEmail(email).orElseThrow(
                ()-> new BadRequestException("Requires a restaurant login")
        );
        food.setName(foodRequest.getName());
        food.setPrice(foodRequest.getPrice());
        food.setRestaurant(loggedRestaurant);
        food.setDescription(foodRequest.getDescription());
        food.setCategories(setCategory(foodRequest.getCategories()));
        food.setDeliveryTime(foodRequest.getDeliveryTime());
        food.setImages(setImages(foodRequest.getFoodImages()));
        Set<Category> availableCategories = loggedRestaurant.getAvailableCategories();
        availableCategories.addAll(food.getCategories());
        availableCategories.remove(categoryRepository.findByName(CategoryEnum.EXTRAS).get());
        loggedRestaurant.setAvailableCategories(availableCategories);
        restaurantRepository.save(loggedRestaurant);
    }

    @Override
    public Page<Food> getAllFoods(FoodPage foodPage) {
        Pageable pageable = Utility.getPageable(foodPage);
        return foodRepository.findAll(pageable);
    }

    @Override
    public Page<Food> searchAllFoods(String keyword) {
        Set<Food> searchedFoods = new HashSet<>();
        List<Food> foodList = foodRepository.findAll();
        List<Food> foods = foodRepository.findByNameContains(keyword);
        List<Food> foodByCategory = foodList.stream().filter(food -> food.getCategories()
                .stream().anyMatch(category -> category.getName().toString()
                        .toLowerCase().contains(keyword))).collect(Collectors.toList());

        searchedFoods.addAll(foods);
        searchedFoods.addAll(foodByCategory);

        FoodPage foodPage = new FoodPage();
        Pageable pageable = Utility.getPageable(foodPage);

        if (keyword != null) {
            return new PageImpl<>(new ArrayList<>(searchedFoods));
        }
        return foodRepository.findAll(pageable);
    }

    @Override
    public Page<Food> getFoodsByCategory(FoodPage foodPage, String categoryName) {
        Category category = getCategory(CategoryEnum.valueOf(categoryName));
        Pageable pageable = Utility.getPageable(foodPage);
        return foodRepository.findAllByCategories(category, pageable);
    }

    @Override
    public Page<Food> getFoodsByRestaurant(FoodPage foodPage, Long restaurantId) {
        Pageable pageable = Utility.getPageable(foodPage);
        return foodRepository.findAllByRestaurantId(restaurantId, pageable);
    }

    @Override
    public Page<Food> getFoodsByRestaurantAndCategory(FoodPage foodPage, String categoryName, Long restaurantId) {
        Category category = getCategory(CategoryEnum.valueOf(categoryName.toUpperCase()));
        Pageable pageable = Utility.getPageable(foodPage);
        return foodRepository.findAllByRestaurantIdAndCategoriesContains(restaurantId, category, pageable);
    }

    private Category getCategory(CategoryEnum categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(
                () -> {
                    throw  new BadRequestException("Category not found!");
                });
    }

    private List<Category> setCategory(List<String> categoryList){

        if(categoryRepository.findByName(CategoryEnum.BREAKFAST).isEmpty()){
            categoryRepository.save(new Category(CategoryEnum.BREAKFAST));
            categoryRepository.save(new Category(CategoryEnum.BRUNCH));
            categoryRepository.save(new Category(CategoryEnum.LUNCH));
            categoryRepository.save(new Category(CategoryEnum.DINNER));
            categoryRepository.save(new Category(CategoryEnum.EXTRAS));
            categoryRepository.save(new Category(CategoryEnum.INSTANT));
        }
        return categoryList.stream().map(this::createCategory).collect(Collectors.toList());
    }

    private List<Image> setImages(List<String> imageList){
        return imageList.stream().map(Image::new).collect(Collectors.toList());
    }

    private Category createCategory(String categoryItem) {
        return categoryRepository.findByName(CategoryEnum.valueOf(categoryItem)).get();
    }
}
