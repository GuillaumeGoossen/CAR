package com.car.TP2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

/*
 * This class represents a user
 */
@Entity
@Table(name = "customer")
public class User {
    
    @Id
    private String email;
    private String password;
    private String name;
    private String surname;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public User() {
    }

    public User(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
