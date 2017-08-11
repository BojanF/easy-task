package com.easytask.model.jpa;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Bojan on 6/7/2017.
 */

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 5000)
    private String text;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnore
    private Project project;


    //getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
    }

    public String getText() {
        return text;
    }

    public DateTime getDate() {
        return date;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String TEXT = "text";

        public static String DATE = "date";

        public static String USER = "user";

        public static String PROJECT = "project";

    }
}
