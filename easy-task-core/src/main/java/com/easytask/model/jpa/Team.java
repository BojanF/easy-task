package com.easytask.model.jpa;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bojan on 6/7/2017.
 */

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @OneToOne
    private Leader leader;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<User>();


    //getters
    public Long getId() {
        return id;
    }

    public Leader getLeader() {
        return leader;
    }

    public Set<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        this.users.remove(user);
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String NAME = "name";

        public static String LEADER = "leader";

        public static String USERS = "users";
    }
}
