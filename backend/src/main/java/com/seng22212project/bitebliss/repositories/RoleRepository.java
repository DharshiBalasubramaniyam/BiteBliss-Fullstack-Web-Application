package com.seng22212project.bitebliss.repositories;

import com.seng22212project.bitebliss.enums.ERole;
import com.seng22212project.bitebliss.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(ERole roleName);
}
