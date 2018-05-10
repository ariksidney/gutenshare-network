package com.group4.gutenshareweb.infrastructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Login")
public class LoginController {

    @Autowired
    private LoginHelper loginHelper;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List listUser() {
        return loginHelper.findAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void create(@RequestBody String username, @RequestBody String password, @RequestBody String firstname,
                       @RequestBody String lastname, @RequestBody String mail) {

        loginHelper.save(username, password, firstname, lastname, mail);
    }

}
