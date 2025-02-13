package com.car.TP2.service;

import com.car.TP2.entity.User;
import com.car.TP2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.car.TP2.interfaces.UserInterface;

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

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}