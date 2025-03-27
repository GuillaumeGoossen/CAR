package com.car.TP2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/*
 * This class represents an order
 */

@Entity
@Table(name = "commande")
public class Order {
    
    @Id
    @GeneratedValue
    private Long id;
    private String title;

    @ManyToOne
    @JoinColumn(name = "customer_email")
    private User customer;

    @OneToMany(mappedBy = "order")
    private List<Item> items;

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

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getCustomer() {
        return customer;
    }

    public List<Item> getItems() {
        return items;
    }

    private String status = "PENDING"; 

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
