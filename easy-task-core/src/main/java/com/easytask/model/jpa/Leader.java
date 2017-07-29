package com.easytask.model.jpa;

import javax.persistence.Entity;
import javax.persistence.*;

/**
 * Created by Bojan on 6/7/2017.
 */

@Entity
@Table(name = "leaders")
public class Leader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;


    //getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String USER = "user";
    }
}
