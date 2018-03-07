package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.core.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final List<User> users;

    public UserController() {
        // just for test purposes
    // this class shouldn't return User objects but should return UserDTOs (yet to implement)
        users = new ArrayList<>();
        users.add(new User("Muster", "Max", "test@mail.ch", "maxtest", "test"));
        users.add(new User("Meier", "Franz", "franz@mail.ch", "franztest", "test2"));
        users.add(new User("Mueller", "Fritz", "fritz@mail.ch", "fritztest", "test3"));
    }

    @GetMapping
    public List<User> getUsers() {
        return users;
    }

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
    }
}
