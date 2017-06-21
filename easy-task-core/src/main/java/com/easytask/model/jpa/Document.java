package com.easytask.model.jpa;

import javax.persistence.Entity;

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
    private Integer id;

    @NotNull
    private DateTime postedOn;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Worker worker;

    //getters
    public Integer getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public DateTime getPostedOn() {
        return postedOn;
    }

    public Worker getWorker() {
        return worker;
    }

    //setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setPostedOn(DateTime postedOn) {
        this.postedOn = postedOn;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
