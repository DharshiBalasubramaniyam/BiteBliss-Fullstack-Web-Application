package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.exceptions.ProductNotFoundException;
import com.seng22212project.bitebliss.models.Order;
import com.seng22212project.bitebliss.models.OrderItem;
import com.seng22212project.bitebliss.models.Products;
import com.seng22212project.bitebliss.models.User;
import com.seng22212project.bitebliss.repositories.ProductRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Set;

@Component
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void sendUserRegistrationVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = fromMail;
        String senderName = "BiteBliss";
        String subject = "BiteBliss - Please verify your registration";
        String content = "Dear " + user.getUsername() + ",<br><br>"
                + "<p>Thank you for joining us! We are glad to have you on board.</p><br>"
                + "<p>To complete the registration process, enter the verification code in your device.</p><br>"
                + "<p>verification code: <strong>" + user.getVerificationCode() + "</strong></p><br>"
                + "<p><strong>Please note that the above verification code will be expired within 15 minutes.</strong></p>"
                + "<br>Thank you,<br>"
                + "BiteBliss.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    @Override
    public void sendForgotPasswordVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = fromMail;
        String senderName = "BiteBliss";
        String subject = "BiteBliss - Forgot password[Verify your account]";
        String content = "Dear " + user.getUsername() + ",<br><br>"
                + "<p>If you have lost your password or wish to reset it, enter the below verification code in your device.</p><br>"
                + "<p>verification code: <strong>" + user.getVerificationCode() + "</strong></p><br>"
                + "<p><strong>Please note that the above verification code will be expired within 15 minutes.</strong></p>"
                + "<p>If you did not request a password reset, you can safely ignore this email. " +
                "           Only a person with access to your email can reset your account password.</p>"
                + "<br>Thank you,<br>"
                + "BiteBliss.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    @Override
    public void sendOrderConfirmationEmail(User user, Order order, Set<OrderItem> orderItems) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = fromMail;
        String senderName = "BiteBliss";
        String subject = "BiteBliss - Order confirmation";

        StringBuilder contentBuilder = new StringBuilder("Dear " + user.getUsername() + ",<br><br>"
                + "<h2>Thank you for your order!</h2>"
                + "<p>Your order #" + order.getOrderId() + " has been successfully placed!</p>"
                + "<h3>Order summary</h3>");
        for(OrderItem orderItem: orderItems) {
            Products product = productRepository.findById(orderItem.getProduct()).orElseThrow(ProductNotFoundException::new);
            String description = product.getProductName() + ": " + orderItem.getProductQuantity() + " x " + product.getPrice() + "<br>";
            contentBuilder.append(description);
        }

        String content = contentBuilder.toString();

        content += "<h4>Total: " + order.getOrderAmt() + "</h4>"
                + "<p>Delivery charges be will added to your total at your doorstep!</p>"
                + "<br>Thank you,<br>"
                + "BiteBliss.";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

}
