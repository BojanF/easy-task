package com.easytask.model.jpa;

import javax.persistence.Entity;

import com.easytask.model.enums.State;
import org.joda.time.DateTime;

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
    private Integer id;

    @ManyToOne
    private Team team;

    @NotNull
    private String name;

    @NotNull
    private DateTime startedOn;

    private DateTime finishedOn;

    @NotNull
    private DateTime deadline;

    @NotNull
    private State projectState;


    //getters
    public Integer getId() {
        return id;
    }

    public Team getTeam() {
        return team;
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


    //setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setTeam(Team team) {
        this.team = team;
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
}
