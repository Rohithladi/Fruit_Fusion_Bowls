package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;



@Service
public class Itemservice {

    @Autowired
    private Itemrepo itemRepository;



    public Item updateItem(Long id, String name, String description, Double cost, Double monthCost, String itemList, MultipartFile image) {
        Optional<Item> existingItemOpt = itemRepository.findById(id);
        if (existingItemOpt.isPresent()) {
            Item existingItem = existingItemOpt.get();

            // Update the fields
            existingItem.setName(name);
            existingItem.setDescription(description);
            existingItem.setCost(cost);
            existingItem.setMonthcost(monthCost); // Update month cost
            existingItem.setItemlist(itemList);  // Update item list

            // Handle image update
            if (image != null && !image.isEmpty()) {
                try {
                    existingItem.setImage(image.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Save and return updated item
            return itemRepository.save(existingItem);
        }

        return null; // Item not found
    }

    
    public boolean deleteItem(Long id) {
        Optional<Item> existingItemOpt = itemRepository.findById(id);
        if (existingItemOpt.isPresent()) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
