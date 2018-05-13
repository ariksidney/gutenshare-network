package com.group4.core;

import com.google.common.base.Preconditions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represents a User of the gutenshare network. A user can upload and download documents aswell as commenting
 * and rating them.
 *
 * @author Arik Sidney Guggenheim
 * @version 1.0
 */
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Empty constructor needed for JPA
     */
    public User() {
        // FOR JPA
    }

    /**
     * Constructor to create an instance of a User based on a UserBuilder.
     *
     * @param builder Instance of UserBuilder
     */
    public User(UserBuilder builder) {
        this.username = Preconditions.checkNotNull(builder.username);
        this.name = Preconditions.checkNotNull(builder.name);
        this.surname = Preconditions.checkNotNull(builder.surname);
        this.mail = Preconditions.checkNotNull(builder.mail);
        this.password = Preconditions.checkNotNull(builder.password);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public static class UserBuilder {

        private String username;
        private String name;
        private String surname;
        private String mail;
        private String password;

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public UserBuilder setPassword(String pw) {
            this.password = pw;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
