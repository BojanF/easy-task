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
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String surename;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    @Enumerated
    private Role role;

    @ManyToMany(mappedBy = "workers", fetch = FetchType.EAGER)
    private Set<Team> teams = new HashSet<Team>();

    @ManyToMany(mappedBy = "workers", fetch = FetchType.EAGER)
    private Set<Task> tasks = new HashSet<Task>();

    public Integer getId() {
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



}
