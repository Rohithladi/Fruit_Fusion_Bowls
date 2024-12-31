package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fruits")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    
    
    @Autowired
    private FruitRepo fruitRepository ;
    // Get all fruits
    @GetMapping
    public List<Fruit> getAllFruits() {
        return fruitService.getAllFruits();
    }

    // Get fruit by ID
    @GetMapping("/{id}")
    public ResponseEntity<Fruit> getFruitById(@PathVariable Long id) {
        return fruitService.getFruitById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add new fruit
    @PostMapping
    public Fruit addFruit(@RequestParam("name") String name,
                          @RequestParam("price") double price,
                          @RequestParam("image") MultipartFile image) throws IOException {
        Fruit fruit = new Fruit();
        fruit.setName(name);
        fruit.setPrice(price);
        fruit.setImage(image.getBytes());
        return fruitService.addFruit(fruit);
    }

    // Update fruit details
    @PutMapping("/{id}")
    public ResponseEntity<Fruit> updateFruit(@PathVariable Long id,
                                             @RequestParam("name") String name,
                                             @RequestParam("price") double price,
                                             @RequestParam(value = "image", required = false) MultipartFile image,
                                             @RequestParam(value = "selected", required = false) Boolean selected) throws IOException {
        // Prepare updated fruit object
        Fruit updatedFruit = new Fruit();
        updatedFruit.setName(name);
        updatedFruit.setPrice(price);

        // If an image is provided, set it in the updated fruit object
        if (image != null && !image.isEmpty()) {
            updatedFruit.setImage(image.getBytes());
        }

        // If selected state is provided, set it in the updated fruit object
        if (selected != null) {
            updatedFruit.setSelected(selected);
        }

        // Call the service to update the fruit in the database
        Fruit fruit = fruitService.updateFruit(id, updatedFruit);

        // Return the updated fruit or 404 Not Found if fruit is not found
        return fruit != null ? ResponseEntity.ok(fruit) : ResponseEntity.notFound().build();
    }


    // Delete fruit
    

    // Toggle selection for a fruit
    @PutMapping("/{id}/select")
    public ResponseEntity<Fruit> toggleFruitSelection(@PathVariable Long id, @RequestBody Map<String, Boolean> requestBody) {
        Boolean selected = requestBody.get("selected");
        Fruit updatedFruit = fruitService.toggleFruitSelection(id, selected);
        
        if (updatedFruit != null) {
            return ResponseEntity.ok(updatedFruit);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFruit(@PathVariable Long id) {
        boolean isRemoved = fruitService.deleteFruit(id);

        return isRemoved ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
    @GetMapping("/selected")
    public List<Fruit> getSelectedFruits() {
        return fruitRepository.findBySelected(true);
    }
    
    

}
