package com.easytask.model.jpa;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

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
    private String name;

    private String icon;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @NotNull
    private Long size;

    @NotNull
    @JsonIgnore
    private Blob data;

    @ManyToOne
    @JsonIgnore
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

    public String getName() {
        return name;
    }

    public Blob getData() {
        return data;
    }

    public Long getSize() {
        return size;
    }

    public Project getProject() {
        return project;
    }

    public User getUser() {
        return user;
    }

    public String getIcon() {
        return icon;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(DateTime date){
        this.date = date;
    }

    public void setName(String name) {
         this.name = name;
    }

    public void setData(Blob data) {
        this.data=data;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @JsonProperty
    public void setProject(Project project) {
        this.project = project;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String DATE = "date";

        public static String NAME = "name";

        public static String PROJECT = "project";

        public static String USER = "user";

        public static String SIZE = "size";

        public static String ICON = "icon";
    }
}
