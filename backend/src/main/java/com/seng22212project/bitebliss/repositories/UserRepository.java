package com.seng22212project.bitebliss.repositories;

import com.seng22212project.bitebliss.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByVerificationCode(String verificationCode);

    User findByEmail(String email);
}
