package com.easytask.model.jpa;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;
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
    private Integer id;

    @NotNull
    @Column(length = 5000)
    private String text;

    @NotNull
    private DateTime date;

    @ManyToOne
    private Worker worker;

    @ManyToOne
    private Project project;


    //getters
    public Integer getId() {
        return id;
    }

    public Worker getWorker() {
        return worker;
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
    public void setId(Integer id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
