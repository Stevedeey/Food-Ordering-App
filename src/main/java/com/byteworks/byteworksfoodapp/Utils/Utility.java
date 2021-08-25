package com.byteworks.byteworksfoodapp.Utils;

import com.byteworks.byteworksfoodapp.Models.Enums.ERole;
import com.byteworks.byteworksfoodapp.Models.FoodPage;
import com.byteworks.byteworksfoodapp.Models.Role;
import com.byteworks.byteworksfoodapp.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Utility {

    private final RoleRepository roleRepository;

    @Autowired
    public Utility(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static Pageable getPageable(FoodPage foodPage) {
        Sort sort = Sort.by(foodPage.getSortDirection(), foodPage.getSortBy());
        return PageRequest.of(foodPage.getPageNumber(), foodPage.getPageSize(), sort);
    }

    public List<Role> assignRole(ERole erole) {
        List<Role> restaurantRoles = new ArrayList<>();
        if(roleRepository.findByName(erole).isPresent()){
            restaurantRoles.add(roleRepository.findByName(erole).get());
            return restaurantRoles;
        }
        Role role = new Role();
        role.setName(erole);
        roleRepository.save(role);
        restaurantRoles.add(role);
        return restaurantRoles;
    }
}