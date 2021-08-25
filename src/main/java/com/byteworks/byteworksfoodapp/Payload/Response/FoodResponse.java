package com.byteworks.byteworksfoodapp.Payload.Response;

import com.byteworks.byteworksfoodapp.Models.Category;
import com.byteworks.byteworksfoodapp.Models.Image;
import com.byteworks.byteworksfoodapp.Models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {

    private Long foodId;

    private String name;

    private Restaurant restaurant;

    private Integer price;

    private String description;

    private List<Category> categories;

    private String preparationTime;

    private List<Image> images;

    private boolean isAvailable;

    private boolean isDeleted;

}