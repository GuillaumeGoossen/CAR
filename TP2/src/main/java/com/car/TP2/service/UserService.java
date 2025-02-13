package com.car.TP2.service;

import com.car.TP2.entity.User;
import com.car.TP2.repository.UserRepository;
import com.car.TP2.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

/*
 * This class represents a user service
 */
@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(String email, String password, String firstName, String lastName) {
        User user = new User(email, password, firstName, lastName);
        userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }

    public void handleLogin(String email, String password, HttpSession session) {
        User user = login(email, password);
        if (user != null) {
            session.setAttribute("userId", user.getEmail());
        }
    }

    public boolean handleSignin(String email, String password, String name, String surname, HttpSession session) {
        if (existsByEmail(email)) {
            return false;
        } else {
            createUser(email, password, name, surname);
            User user = findByEmail(email);
            session.setAttribute("userId", user.getEmail());
            return true;
        }
    }

    public void handleLogout(HttpSession session) {
        session.invalidate();
    }
}