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
    private Integer quantity;
    private Double price;

    @ManyToOne
    private Order order;

    public Item() {
    }

    public Item(Long id, String wording, Integer quantity, Double price) {
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

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
