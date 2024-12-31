package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;



@Service
public class AsyncEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendOrderEmailToAdmin(Orders order) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo("fruitfusionbowl@gmail.com");
            helper.setSubject("New Order Received: Order #" + order.getId());

            // Load the template and set variables
            Context context = new Context();
            context.setVariable("order", order);
            String htmlContent = templateEngine.process("order-email", context);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @Async
    public void sendOrdersEmailToAdmin(Orders order) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
 
            helper.setTo("fruitfusionbowl@gmail.com");
            helper.setSubject("New Order Received: Order #" + order.getId());
 
            // Load the template and set variables
            Context context = new Context();
            context.setVariable("order", order);
            String htmlContent = templateEngine.process("Fruit-email", context);

            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
