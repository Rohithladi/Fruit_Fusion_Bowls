package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:3000") // Adjust for your frontend URL
public class Itemcontroller {

    private final Itemrepo itemRepository;
    
    @Autowired
    private Itemservice itemService;


    public Itemcontroller(Itemrepo itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Add a new item
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> addItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("additionalDescription") String additionalDescription,  // New field
            @RequestParam("cost") double cost,
            @RequestParam("monthlyPackage") double monthlyPackage,  // New field
            @RequestParam("image") MultipartFile image
    ) {
        try {
            // Convert image to byte array
            byte[] imageData = image.getBytes();

            // Create and save item
            Item item = new Item();
            item.setName(name);
            item.setDescription(description);
            item.setItemlist(additionalDescription);  // New field
            item.setCost(cost);
            item.setMonthcost(monthlyPackage);  // New field
            item.setImage(imageData);

            itemRepository.save(item);

            return ResponseEntity.ok("Item added successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving item: " + e.getMessage());
        }
    }

    // Get all items
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return ResponseEntity.ok(items);
    }

    // Get image by item ID
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"item-image.jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(item.getImage());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("cost") Double cost,
            @RequestParam(value = "monthcost", required = false) Double monthCost,
            @RequestParam(value = "itemlist", required = false) String itemList,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Item updatedItem = itemService.updateItem(id, name, description, cost, monthCost, itemList, image);
        return updatedItem != null ? ResponseEntity.ok(updatedItem) : ResponseEntity.notFound().build();
    }

    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        boolean isDeleted = itemService.deleteItem(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    } 
}
