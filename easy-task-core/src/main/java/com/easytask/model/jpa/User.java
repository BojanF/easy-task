package com.easytask.model.jpa;

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

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Team> teams = new HashSet<Team>();

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<Task>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coworkers> coworkers = new HashSet<Coworkers>();

    @OneToMany(mappedBy = "coworker", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coworkers> coworkersOf = new HashSet<Coworkers>();

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

    public Set<Team> getTeams() {
        return teams;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public Set<Coworkers> getCoworkers() {
        return coworkers;
    }

    public Set<Coworkers> getCoworkersOf() {
        return coworkersOf;
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

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void setCoworkers(Set<Coworkers> coworkers) {
        this.coworkers = coworkers;
    }

    public void setCoworkersOf(Set<Coworkers> coworkersOf) {
        this.coworkersOf = coworkersOf;
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String NAME = "name";

        public static String SURNAME = "surname";

        public static String USERNAME = "username";

        public static String PASSWORD = "password";

        public static String EMAIL = "email";

        public static String TEAMS = "teams";

        public static String TASKS = "tasks";

        public static String COWORKERS = "coworkers";

        public static String COWORKERS_OF = "coworkersOf";
    }
}
