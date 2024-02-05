package com.seng22212project.bitebliss.factories;

import com.seng22212project.bitebliss.models.ERole;
import com.seng22212project.bitebliss.models.Role;
import com.seng22212project.bitebliss.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {
    @Autowired
    RoleService roleService;

    public Role getInstance(String role) {
        if (role.equals("admin")) {
            return roleService.findByName(ERole.ADMIN);
        }
        else if (role.equals("user")){
            return roleService.findByName(ERole.USER);
        }
        throw new IllegalArgumentException("Invalid role name: " + role);
    }
}
