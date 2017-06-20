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

    @ManyToOne
    private Project project;

    @NotNull
    private DateTime postedOn;

    public Integer getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public DateTime getPostedOn() {
        return postedOn;
    }
}
