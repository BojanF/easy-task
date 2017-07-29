package com.easytask.model.jpa;

import javax.persistence.Entity;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Bojan on 6/7/2017.
 */

@Entity
@Table(name ="documents" )
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @NotNull
    private String url;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User user;


    //getters
    public Long getId() {
        return id;
    }

    public DateTime getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(DateTime date){
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setUser(User user) {
        this.user = user;
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String DATE = "date";

        public static String URL = "url";

        public static String PROJECT = "project";

        public static String USER = "user";
    }
}
