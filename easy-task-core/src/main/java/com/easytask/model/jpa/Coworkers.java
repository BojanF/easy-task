package com.easytask.model.jpa;

import com.easytask.model.enums.CoworkerState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Marijo on 30-Jul-17.
 */

@Entity
@Table(name =  "coworkers")
public class Coworkers implements Serializable{

    @EmbeddedId
    private CoworkerId id;

    @ManyToOne
    @MapsId("userA")
    private User userA; //user that sends request

    @ManyToOne
    @MapsId("userB")
    private User userB;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CoworkerState state;


    //getters
    public CoworkerId getId() {
        return id;
    }

    public User getUserA() {
        return userA;
    }

    public User getUserB() {
        return userB;
    }

    public CoworkerState getState() {
        return state;
    }


    //setters
    public void setId(CoworkerId id) {
        this.id = id;
    }

    public void setUserA(User userA) {
        this.userA = userA;
    }

    public void setUserB(User userB) {
        this.userB = userB;
    }

    public void setState(CoworkerState state) {
        this.state = state;
    }


    //fields
    public static class FIELDS {

        public static String USER_A = "userA";

        public static String USER_B = "userB";

        public static String STATE = "state";
        
    }
    
}
