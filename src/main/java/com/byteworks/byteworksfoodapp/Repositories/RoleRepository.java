package com.byteworks.byteworksfoodapp.Repositories;

import com.byteworks.byteworksfoodapp.Models.Enums.ERole;
import com.byteworks.byteworksfoodapp.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}