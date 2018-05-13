package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity create(@RequestBody UserDto user) {
        try {
            loginHelper.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
