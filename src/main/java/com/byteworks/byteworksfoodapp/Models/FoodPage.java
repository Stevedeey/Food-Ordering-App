package com.byteworks.byteworksfoodapp.Models;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class FoodPage {
    private int pageNumber = 0;
    private int pageSize = 60;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "name";
}
