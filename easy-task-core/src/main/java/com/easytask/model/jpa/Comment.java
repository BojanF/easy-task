package com.easytask.model.jpa;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;
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
    private Long id;

    @NotNull
    @Column(length = 5000)
    private String text;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    @ManyToOne
    private Worker commentOwner;

    @ManyToOne
    private Project onProject;


    //getters
    public Long getId() {
        return id;
    }

    public Worker getCommentOwner() {
        return commentOwner;
    }

    public Project getOnProject() {
        return onProject;
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

    public void setCommentOwner(Worker commentOwner) {
        this.commentOwner = commentOwner;
    }

    public void setOnProject(Project onProject) {
        this.onProject = onProject;
    }
}
