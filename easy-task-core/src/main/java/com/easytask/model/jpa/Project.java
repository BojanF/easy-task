package com.easytask.model.jpa;

import javax.persistence.Entity;

import com.easytask.model.enums.State;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



/**
 * Created by Bojan on 6/7/2017.
 */

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(length = 5000)
    public String description;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startedOn;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime finishedOn;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deadline;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State projectState;

    @ManyToOne
    private Team projectTeam;


    //getters
    public Long getId() {
        return id;
    }

    public Team getProjectTeam() {
        return projectTeam;
    }

    public String getName() {
        return name;
    }

    public DateTime getStartedOn() {
        return startedOn;
    }

    public DateTime getFinishedOn() {
        return finishedOn;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public State getProjectState() {
        return projectState;
    }

    public String getDescription() {
        return description;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setProjectTeam(Team team) {
        this.projectTeam = team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartedOn(DateTime startedOn) {
        this.startedOn = startedOn;
    }

    public void setFinishedOn(DateTime finishedOn) {
        this.finishedOn = finishedOn;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public void setProjectState(State projectState) {
        this.projectState = projectState;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class FIELDS {
        public static String TEAM = "projectTeam";
    }
}
