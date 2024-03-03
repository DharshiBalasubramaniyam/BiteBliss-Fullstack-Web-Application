package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.enums.ERole;
import com.seng22212project.bitebliss.models.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role findByName(ERole eRole);
}