package com.example.demo;

import org.springframework.web.multipart.MultipartFile;


public class FruitDto {
    private String name;
    private double price;
    private MultipartFile image; // Uploaded image file
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
    
    
    
    
}
