package com.byteworks.byteworksfoodapp.Models;


import com.byteworks.byteworksfoodapp.Models.Enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private CategoryEnum name;

    public Category(CategoryEnum name) {
        this.name = name;
    }
}
