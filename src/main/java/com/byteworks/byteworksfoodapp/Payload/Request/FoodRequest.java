package com.byteworks.byteworksfoodapp.Payload.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodRequest {

    @NotBlank(message = "Food must have a name")
    private String name;

    @NotNull(message = "Food must have a price")
    private Integer price;

    private String description;

    private List<String> categories;

    private String deliveryTime;

    private List<String> foodImages;

}