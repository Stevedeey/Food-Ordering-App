package com.byteworks.byteworksfoodapp.Repositories;

import com.byteworks.byteworksfoodapp.Models.Category;
import com.byteworks.byteworksfoodapp.Models.Enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryEnum categoryEnum);
}
