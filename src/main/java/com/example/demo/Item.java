package com.example.demo;

import java.util.Arrays;
import jakarta.persistence.*;


@Entity

public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 500)
    private String description;

    private double cost;
     private String itemlist ;
     
     private double monthcost;
    
    @Lob
    private byte[] image; 
    
    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	} 

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	
	

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", cost=" + cost + ", image="
				+ Arrays.toString(image) + "]";
	}

	public String getItemlist() {
		return itemlist;
	}

	public void setItemlist(String itemlist) {
		this.itemlist = itemlist;
	}

	public double getMonthcost() {
		return monthcost;
	}

	public void setMonthcost(double monthcost) {
		this.monthcost = monthcost;
	}
    
    
}
