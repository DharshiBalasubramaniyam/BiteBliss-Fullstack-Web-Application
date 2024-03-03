package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.models.Order;
import com.seng22212project.bitebliss.models.OrderItem;
import com.seng22212project.bitebliss.models.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

@Service
public interface NotificationService {
    void sendUserRegistrationVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    void sendForgotPasswordVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    void sendOrderConfirmationEmail(User user, Order order, Set<OrderItem> orderItems) throws MessagingException, UnsupportedEncodingException;


}
