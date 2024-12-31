package com.example.demo;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Orderservice {
    @Autowired
    private Orderrepo orderRepository;

    public Orders saveOrder(Orders order) {
        return orderRepository.save(order);
    }
    
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
    
    
    public boolean deleteOrder(Long id) {
        Optional<Orders> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.delete(order.get());
            return true;
        }
        return false;
    }
}
 