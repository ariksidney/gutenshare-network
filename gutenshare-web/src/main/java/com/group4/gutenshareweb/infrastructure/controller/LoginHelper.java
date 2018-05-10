package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.UserDto;
import com.group4.api.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LoginHelper implements UserDetailsService {

    private final UserService userService;

    public LoginHelper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userService.getUserByUsername(username);
        if (userDto == null) {
            throw new IllegalArgumentException("User not found");
        }

        return new User(userDto.getUsername(), userDto.getPassword(), getAuthority());

    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List findAll() {
        return userService.getAllUsers();
    }

    public void save(String username, String password, String firstname, String lastname, String mail) {
        userService.save(username, password, firstname, lastname, mail);
    }
}
