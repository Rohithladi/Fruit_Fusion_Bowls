package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FruitService {

    @Autowired
    private FruitRepo fruitRepository;

    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    public Optional<Fruit> getFruitById(Long id) {
        return fruitRepository.findById(id);
    }

    public Fruit addFruit(Fruit fruit) {
        return fruitRepository.save(fruit);
    }

    public Fruit updateFruit(Long id, Fruit updatedFruit) {
        Optional<Fruit> existingFruitOptional = fruitRepository.findById(id);
        
        if (existingFruitOptional.isPresent()) {
            Fruit existingFruit = existingFruitOptional.get();

            // Only update name if it's changed
            if (updatedFruit.getName() != null && !updatedFruit.getName().equals(existingFruit.getName())) {
                existingFruit.setName(updatedFruit.getName());
            }

            // Only update price if it's changed
            if (updatedFruit.getPrice() != existingFruit.getPrice()) {
                existingFruit.setPrice(updatedFruit.getPrice());
            }

            // Only update image if a new image is provided
            if (updatedFruit.getImage() != null && updatedFruit.getImage().length > 0) {
                existingFruit.setImage(updatedFruit.getImage());
            }

            // Only update selected if it's changed
            if (updatedFruit.isSelected() != existingFruit.isSelected()) {
                existingFruit.setSelected(updatedFruit.isSelected());
            }

            // Save the updated fruit to the database
            return fruitRepository.save(existingFruit);
        }
        
        return null; // Return null if fruit doesn't exist
    }


   

    public Fruit toggleFruitSelection(Long id, Boolean selected) {
        Optional<Fruit> fruitOptional = fruitRepository.findById(id);
        
        if (fruitOptional.isPresent()) {
            Fruit fruit = fruitOptional.get();
            fruit.setSelected(selected); // Assuming a 'selected' field exists on your Fruit model.
            return fruitRepository.save(fruit);
        }

        return null; // Return null if the fruit doesn't exist.
    }
    
    public boolean deleteFruit(Long id) {
        Optional<Fruit> existingFruit = fruitRepository.findById(id);
        if (existingFruit.isPresent()) {
            fruitRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
