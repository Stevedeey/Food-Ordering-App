package com.byteworks.byteworksfoodapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseModel{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @JsonIgnore
    @Column
    private String gender;

    @JsonIgnore
    @Column
    private String phoneNumber;

    @JsonIgnore
    @Column
    private boolean isDeleted = false;

    @JsonIgnore
    @OneToMany
    private List<Address> addresses = new ArrayList<>();

    @JsonIgnore
    @Column(name = "date_of_birth")
    private String dateOfBirth;

    private String password;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User(String firstName, String lastName, String email, String gender, String dateOfBirth, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
