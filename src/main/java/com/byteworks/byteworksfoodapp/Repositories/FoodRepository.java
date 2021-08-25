package com.byteworks.byteworksfoodapp.Repositories;

import com.byteworks.byteworksfoodapp.Models.Category;
import com.byteworks.byteworksfoodapp.Models.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByName(String name);

    Food getByName(String name);

    List<Food> findByNameContains(String keyword);

    Page<Food> findAllByCategories(Category category, Pageable pageable);

    Page<Food> findAllByRestaurantId(Long restaurantId, Pageable pageable);

    Page<Food> findAllByRestaurantIdAndCategoriesContains(Long restaurant_id, Category category, Pageable pageable);
}
