package com.car.TP2.interfaces;

import com.car.TP2.entity.User;
import jakarta.servlet.http.HttpSession;

/*
 * This interface represents a user interface
 */
public interface UserInterface {

    void createUser(String email, String password, String firstName, String lastName);

    User login(String email, String password);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    void handleLogin(String email, String password, HttpSession session);

    boolean handleSignin(String email, String password, String name, String surname, HttpSession session);

    void handleLogout(HttpSession session);
}