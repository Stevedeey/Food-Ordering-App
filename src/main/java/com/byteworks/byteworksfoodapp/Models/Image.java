package com.byteworks.byteworksfoodapp.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseModel {

    @Column(columnDefinition = "TEXT")
    private String image;

}
