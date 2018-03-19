package com.group4.api;

import com.group4.core.User;
import com.group4.core.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserService {

    private final UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserService(UserRepositoryInterface userRepositoryInterface) {
        this.userRepositoryInterface = userRepositoryInterface;
    }

    public List<User> getAllUsers() {
        return userRepositoryInterface.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepositoryInterface.findByUsername(username);
    }

}
