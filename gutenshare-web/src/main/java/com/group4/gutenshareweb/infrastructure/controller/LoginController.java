package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginHelper loginHelper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List listUser() {
        return loginHelper.findAll();
    }

    @PostMapping(value = "/register")
    public void create(@RequestBody UserDto user) {
        loginHelper.save(user);
    }

}
