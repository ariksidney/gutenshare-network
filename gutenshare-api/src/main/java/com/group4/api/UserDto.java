package com.group4.api;

public class UserDto {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String mail;

    public UserDto() {

    }

    public UserDto(String username, String password, String firstname, String lastname, String mail) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
