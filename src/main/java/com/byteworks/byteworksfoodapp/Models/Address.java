package com.byteworks.byteworksfoodapp.Models;

import com.byteworks.byteworksfoodapp.Models.Enums.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "Addresses")
@AllArgsConstructor
@NoArgsConstructor
public class Address extends BaseModel {

    private String name;

    private String phone;

    private String address;

    private City city;

    public Address(String address, City city) {
        this.address = address;
        this.city = city;
    }

    public Address(String address) {
        super();
    }
}