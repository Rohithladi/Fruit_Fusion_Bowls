package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    @Autowired
    private Orderservice orderService;
    
    @Autowired
    private AsyncEmailService emailService;

    
    
    
    @PostMapping
    public ResponseEntity<Orders> saveOrder(@RequestBody Orders order) {
        // Calculate total cost
        double totalCost = order.getItems().stream()
                .mapToDouble(item -> item.getCost() * item.getQuantity())
                .sum();
        order.setTotalCost(totalCost);

        // Save the order
        Orders savedOrder = orderService.saveOrder(order);

        // Trigger email sending asynchronously
   emailService.sendOrderEmailToAdmin(savedOrder);
 
        // Return response immediately after saving the order
        return ResponseEntity.ok(savedOrder);
    }
    @PostMapping("/fruit") 
    public ResponseEntity<Orders> saveOrders(@RequestBody Orders order) {
        // Calculate total cost
        double totalCost = order.getItems().stream()
                .mapToDouble(item -> item.getCost() * item.getQuantity())
                .sum();
        order.setTotalCost(totalCost);

        // Save the order
        Orders savedOrder = orderService.saveOrder(order);
 
        // Trigger email sending asynchronously 
    emailService.sendOrdersEmailToAdmin(savedOrder);
 
        // Return response immediately after saving the order
        return ResponseEntity.ok(savedOrder);
    }
    
    
    @GetMapping
    public List<Orders> getOrders() {
        return orderService.getAllOrders();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted) {
            return ResponseEntity.ok("Order deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Order not found.");
        }
    }
}
