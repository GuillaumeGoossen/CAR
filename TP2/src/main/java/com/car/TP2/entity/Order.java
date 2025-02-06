package com.car.TP2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/*
 * This class represents an order
 */

@Entity
public class Order {
    
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    public Order() {
    }

    public Order(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
