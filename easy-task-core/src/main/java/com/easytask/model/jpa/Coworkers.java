package com.easytask.model.jpa;

import com.easytask.model.enums.CoworkerState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Marijo on 30-Jul-17.
 */

@Entity
@Table(name =  "coworkers")
public class Coworkers implements Serializable{

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "coworker_id")
    private User coworker;

    @NotNull
    private CoworkerState state;

    //getters
    public User getUser() {
        return user;
    }

    public User getCoworker() {
        return coworker;
    }

    public CoworkerState getState() {
        return state;
    }


    //setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setCoworker(User coworker) {
        this.coworker = coworker;
    }

    public void setState(CoworkerState state) {
        this.state = state;
    }


    //fields
    public static class FIELDS {

        public static String USER = "user";

        public static String COWORKER = "coworker";

        public static String STATE = "state";
        
    }
    
}
