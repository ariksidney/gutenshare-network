package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.UserService;
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
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
        // just for test purposes
        // this class shouldn't return User objects but should return UserDTOs (yet to implement)
        users = new ArrayList<>();
        users.add(new User.UserBuilder().setUsername("maxtest").setName("Muster").setSurname("Max").setMail("test@mail.ch").setPassword("test").build());
        users.add(new User.UserBuilder().setUsername("fritztest").setName("Meier").setSurname("Fritz").setMail("test2@mail.ch").setPassword("test2").build());
        users.add(new User.UserBuilder().setUsername("hanstest").setName("Mueller").setSurname("Hans").setMail("test3@mail.ch").setPassword("test3").build());
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{username}")
    public User getUserByUsername(@PathVariable String username) {

        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
    }
}
