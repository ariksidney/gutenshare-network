package com.group4.api;

import com.group4.core.User;
import com.group4.core.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserService {

    private final UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserService(UserRepositoryInterface userRepositoryInterface) {
        this.userRepositoryInterface = userRepositoryInterface;
    }

    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        userRepositoryInterface.findAll().forEach(user -> userDtos.add(toUserDto(user)));
        return userDtos;
    }

    public UserDto getUserByUsername(String username) {
        return toUserDto(userRepositoryInterface.findByUsername(username));
    }

    public void save(String username, String password, String firstname, String lastname, String mail) {
        User user = new User.UserBuilder().setUsername(username).setPassword(password).setSurname(firstname).setName
                (lastname).setMail(mail).build();
        userRepositoryInterface.save(user);
    }

    private UserDto toUserDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user
                .getMail());
    }
}
