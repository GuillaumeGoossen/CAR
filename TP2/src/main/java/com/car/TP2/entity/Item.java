package com.car.TP2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/*
 * This class represents an item
 */

@Entity
@Table(name = "item")
public class Item {
    
    @Id
    @GeneratedValue
    private Long id;
    private String wording;
    private String quantity;
    private String price;

    @ManyToOne
    private Order order;

    public Item() {
    }

    public Item(Long id, String wording, String quantity, String price) {
        this.id = id;
        this.wording = wording;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getWording() {
        return wording;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
