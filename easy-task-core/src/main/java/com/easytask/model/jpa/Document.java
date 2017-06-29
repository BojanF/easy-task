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
    private DateTime postedOn;

    @NotNull
    private String url;

    @ManyToOne
    private Project forProject;

    @ManyToOne
    private Worker fromWorker;

    //getters
    public Long getId() {
        return id;
    }

    public Project getProject() {
        return forProject;
    }

    public DateTime getPostedOn() {
        return postedOn;
    }

    public Worker getWorker() {
        return fromWorker;
    }

    public String getUrl() {
        return url;
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProject(Project project) {
        this.forProject = project;
    }

    public void setPostedOn(DateTime postedOn) {
        this.postedOn = postedOn;
    }

    public void setWorker(Worker worker) {
        this.fromWorker = worker;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
