package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.enums.ERole;
import com.seng22212project.bitebliss.models.Role;
import com.seng22212project.bitebliss.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(ERole eRole) {
        return roleRepository.findByRoleName(eRole)
                .orElseThrow(() -> new RuntimeException("Role is not found."));
    }
}
