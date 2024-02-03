package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.SignUpRequestDto;
import com.seng22212project.bitebliss.factories.RoleFactory;
import com.seng22212project.bitebliss.models.Role;
import com.seng22212project.bitebliss.models.User;
import com.seng22212project.bitebliss.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationService notificationService;

    @Autowired
    RoleFactory roleFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.verificationCodeExpirationMs}")
    private long EXPIRY_PERIOD;

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ResponseEntity<String> save(SignUpRequestDto signUpRequestDto) throws MessagingException, UnsupportedEncodingException {

        if (existsByUsername(signUpRequestDto.getUserName())) {
            return ResponseEntity.badRequest().body("Registration Failed: Username is already taken!");
        }
        if (existsByEmail(signUpRequestDto.getEmail())) {
            return ResponseEntity.badRequest().body("Registration Failed: Email is already taken!");
        }

        User user = new User(
                signUpRequestDto.getUserName(),
                signUpRequestDto.getEmail(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                generateVerificationCode(),
                calculateCodeExpirationTime(),
                false,
                determineRoles(signUpRequestDto.getRoles())
        );

        userRepository.save(user);
        notificationService.sendUserRegistrationVerificationEmail(user);

        return ResponseEntity.ok("Verification email has been sent successfully!");

    }

    @Override
    public boolean verifyVerificationCode(String code) {
        User user = userRepository.findByVerificationCode(code);

        if (user == null || user.isEnabled()) {
            System.out.println("verification failed: Invalid verification code");
            return false;
        }

        long currentTimeInMs = System.currentTimeMillis();
        long codeExpiryTimeInMillis = user.getVerificationCodeExpiryTime().getTime();

        if (currentTimeInMs > codeExpiryTimeInMillis) {
            System.out.println("verification failed: Verification code has been expired!");
            return false;
        }

        else {
            user.setVerificationCode(null);
            user.setVerificationCodeExpiryTime(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public ResponseEntity<String> resendVerificationCode(String email) throws MessagingException, UnsupportedEncodingException {
        if(!existsByEmail(email)) {
            return ResponseEntity.ok("Cannot send verification code: Email not found!");
        }
        User user = findByEmail(email);

        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiryTime(calculateCodeExpirationTime());
        user.setEnabled(false);

        userRepository.save(user);
        notificationService.sendUserRegistrationVerificationEmail(user);

        return ResponseEntity.ok("Verification email has been resent successfully!");

    }

    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 1000000));
    }

    private Date calculateCodeExpirationTime() {
        long currentTimeInMs = System.currentTimeMillis();
        return new Date(currentTimeInMs + EXPIRY_PERIOD);
    }

    private Set<Role> determineRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            roles.add(roleFactory.getInstance("user"));
        } else {
            strRoles.forEach(role -> {
                roles.add(roleFactory.getInstance(role));
            });
        }
        return roles;
    }
}
