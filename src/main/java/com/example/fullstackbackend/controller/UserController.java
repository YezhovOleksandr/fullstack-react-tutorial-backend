package com.example.fullstackbackend.controller;

import com.example.fullstackbackend.dao.UserDao;
import com.example.fullstackbackend.exception.UserNotFoundException;
import com.example.fullstackbackend.model.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class UserController {
    private final UserDao userDao;

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return userDao.save(newUser);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userDao.findAll();
    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userDao.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
        return userDao.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userDao.save(user);
                }).orElseThrow(() ->new UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        if (!userDao.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userDao.deleteById(id);
        return "User with id" + id + "has been deleted";
    }
}
