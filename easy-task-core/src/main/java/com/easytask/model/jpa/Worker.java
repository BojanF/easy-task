package com.easytask.model.jpa;

import javax.persistence.Entity;

//import Role;



import com.easytask.model.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bojan on 6/7/2017.
 */
@Entity
@Table(name =  "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String surename;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    @Enumerated
    private Role role;

    @ManyToMany(mappedBy = "workers", fetch = FetchType.EAGER)
    private Set<Team> teams = new HashSet<Team>();

    @ManyToMany(mappedBy = "workers", fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<Task>();


    //getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurename() {
        return surename;
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

    public Role getRole() {
        return role;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public Set<Task> getTasks() {
        return tasks;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurename(String surename) {
        this.surename = surename;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
