package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.requests.ResetPasswordRequestDto;
import com.seng22212project.bitebliss.dtos.requests.SignUpRequestDto;
import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.enums.ApiResponseStatus;
import com.seng22212project.bitebliss.exceptions.*;
import com.seng22212project.bitebliss.factories.RoleFactory;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.repositories.OrderItemRepository;
import com.seng22212project.bitebliss.repositories.OrderRepository;
import com.seng22212project.bitebliss.repositories.ProductRepository;
import com.seng22212project.bitebliss.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    RoleFactory roleFactory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Value("${app.verificationCodeExpirationMs}")
    private long EXPIRY_PERIOD;

    @Override
    public ResponseEntity<ApiResponseDto<?>> save(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException, UserServiceLogicException {
        if (existsByUsername(signUpRequestDto.getUserName())) {
            throw new UserAlreadyExistsException("Registration Failed: username is already taken!");
        }
        if (existsByEmail(signUpRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: email is already taken!");
        }

        try {
            User user = createUser(signUpRequestDto);

            userRepository.save(user);
            notificationService.sendUserRegistrationVerificationEmail(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>(
                    ApiResponseStatus.SUCCESS.name(), "Verification email has been successfully sent!"
            ));

        }catch(Exception e) {
            log.error("Registration failed: {}", e.getMessage());
            throw new UserServiceLogicException("Registration failed: Something went wrong!");
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> verifyRegistrationVerification(String code) throws UserVerificationFailedException {
        User user = userRepository.findByVerificationCode(code);

        if (user == null || user.isEnabled()) {
            throw new UserVerificationFailedException("Verification failed: invalid verification code!");
        }

        long currentTimeInMs = System.currentTimeMillis();
        long codeExpiryTimeInMillis = user.getVerificationCodeExpiryTime().getTime();

        if (currentTimeInMs > codeExpiryTimeInMillis) {
            throw new UserVerificationFailedException("Verification failed: expired verification code!");
        }

        user.setVerificationCode(null);
        user.setVerificationCodeExpiryTime(null);
        user.setEnabled(true);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS.name(),  "Verification successful: User account has been successfully created!"
        ));
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> resendVerificationCode(String email) throws UserNotFoundException, UserServiceLogicException {

        User user = findByEmail(email);

        try {
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiryTime(calculateCodeExpirationTime());
            user.setEnabled(false);

            userRepository.save(user);
            notificationService.sendUserRegistrationVerificationEmail(user);

            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDto<>(
                    ApiResponseStatus.SUCCESS.name(), "Verification email has been resent successfully!")
            );
        }catch(Exception e) {
            log.error("Registration verification failed: {}", e.getMessage());
            throw new UserServiceLogicException("Registration failed: Something went wrong!");
        }

    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> verifyEmailAndSendForgotPasswordVerificationEmail(String email) throws UserServiceLogicException, UserNotFoundException {
        if (existsByEmail(email)) {
            try {
                User user = findByEmail(email);
                user.setVerificationCode(generateVerificationCode());
                user.setVerificationCodeExpiryTime(calculateCodeExpirationTime());
                userRepository.save(user);

                notificationService.sendForgotPasswordVerificationEmail(user);
                return ResponseEntity.ok(new ApiResponseDto<>(
                        ApiResponseStatus.SUCCESS.name(),
                        "Verification successful: Email sent successfully!"
                ));
            } catch (Exception e) {
                log.error("Reset password email verification failed: {}", e.getMessage());
                throw new UserServiceLogicException("Verification failed: Something went wrong!");
            }
        }

        throw new UserNotFoundException("Verification failed: User not found with email " + email + "!");

    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> verifyForgotPasswordVerification(String code) throws UserVerificationFailedException, UserServiceLogicException {
        User user = userRepository.findByVerificationCode(code);

        if (user == null) {
            throw new UserVerificationFailedException("Verification failed: invalid verification code!");
        }

        long currentTimeInMs = System.currentTimeMillis();
        long codeExpiryTimeInMillis = user.getVerificationCodeExpiryTime().getTime();

        if (currentTimeInMs > codeExpiryTimeInMillis) {
            throw new UserVerificationFailedException("Verification failed: expired verification code!");
        }

        try {

            user.setVerificationCode(null);
            user.setVerificationCodeExpiryTime(null);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(
                    ApiResponseStatus.SUCCESS.name(), "Verification successful: User account has been verified!"
            ));
        }catch(Exception e) {
            log.error("Reset password verification failed: {}", e.getMessage());
            throw new UserServiceLogicException("Verification failed: Something went wrong!" + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> resetPassword(ResetPasswordRequestDto resetPasswordDto) throws UserNotFoundException, UserServiceLogicException {
        if (existsByEmail(resetPasswordDto.getEmail())) {
            try {
                User user = findByEmail(resetPasswordDto.getEmail());

                if (resetPasswordDto.getCurrentPassword() != null) {
                    if (!passwordEncoder.matches(resetPasswordDto.getCurrentPassword(), user.getPassword())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(
                                ApiResponseStatus.FAILED.name(),
                                "Reset password not successful: current password is incorrect!!"
                        ));
                    }
                }

                user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));

                userRepository.save(user);

                return ResponseEntity.ok(new ApiResponseDto<>(
                        ApiResponseStatus.SUCCESS.name(),
                        "Reset successful: Password has been successfully reset!"
                ));
            }catch(Exception e) {
                log.error("Resetting password failed: {}", e.getMessage());
                throw  new UserServiceLogicException("Reset failed: Password cannot be reset!");
            }
        }

        throw new UserNotFoundException("User not found with email " + resetPasswordDto.getEmail());
    }



    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email " +  email));
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

    private User createUser(SignUpRequestDto signUpRequestDto) {
        return new User(
                signUpRequestDto.getUserName(),
                signUpRequestDto.getEmail(),
                passwordEncoder.encode(signUpRequestDto.getPassword()),
                generateVerificationCode(),
                calculateCodeExpirationTime(),
                false,
                determineRoles(signUpRequestDto.getRoles())
        );
    }

}
