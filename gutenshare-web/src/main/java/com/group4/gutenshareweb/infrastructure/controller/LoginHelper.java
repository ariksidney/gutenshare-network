package com.group4.gutenshareweb.infrastructure.controller;

import com.group4.api.UserDto;
import com.group4.api.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LoginHelper implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginHelper(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
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

    public void save(UserDto userDto) throws IllegalArgumentException {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.save(userDto);
    }
}
