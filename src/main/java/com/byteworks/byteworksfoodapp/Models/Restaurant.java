package com.byteworks.byteworksfoodapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseModel{

    @Column(name = "name")
    private String name;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @JsonIgnore
    @OneToOne
    private Address address;

    @Column
    private boolean isActivated = true;

    @Column
    private boolean isDeleted;

    @Column(name = "available_categories")
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Category> availableCategories = new HashSet<>();

    @JsonIgnore
    private String password;

    @Column
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurant_roles",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public Restaurant(String name, String email, String phoneNumber, Address address, String password) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.password = password;
    }
}