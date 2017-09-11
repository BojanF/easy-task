package com.easytask.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bojan on 6/7/2017.
 */

@Entity
@Table(name =  "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"username","email"})})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    private String username;


    @NotNull
    private String password;

    @NotNull
    private String email;


    //getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String NAME = "name";

        public static String SURNAME = "surname";

        public static String USERNAME = "username";

        public static String PASSWORD = "password";

        public static String EMAIL = "email";

    }
}
