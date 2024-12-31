package com.example.demo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepo extends JpaRepository<Fruit, Long> {

    List<Fruit> findBySelected(boolean selected); // Find all fruits where selected is true
    
}
