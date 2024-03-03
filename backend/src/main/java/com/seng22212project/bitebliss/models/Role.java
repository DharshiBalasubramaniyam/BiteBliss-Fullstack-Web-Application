package com.seng22212project.bitebliss.models;

import com.seng22212project.bitebliss.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Enumerated(EnumType.STRING)
    @Column(length = 25)
    private ERole roleName;

    public Role(ERole roleName) {
        this.roleName = roleName;
    }

}
