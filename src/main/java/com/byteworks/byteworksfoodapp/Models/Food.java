package com.byteworks.byteworksfoodapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "food")
@AllArgsConstructor
@NoArgsConstructor
public class Food extends BaseModel{

    @Column
    private String name;

    @ManyToOne
    private Restaurant restaurant;

    @Column
    private Integer price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Category> categories;

    @Column
    private String preparationTime;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @Column
    private boolean isAvailable = true;

    @JsonIgnore
    @Column
    private boolean isDeleted = false;

}
