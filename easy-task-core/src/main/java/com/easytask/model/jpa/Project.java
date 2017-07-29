package com.easytask.model.jpa;

import javax.persistence.Entity;

import com.easytask.model.enums.ProjectState;
import org.hibernate.annotations.Type;
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
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Column(length = 5000)
    public String description;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createdOn;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime completedOn;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deadline;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProjectState state;

    @ManyToOne
    private Team team;


    //getters
    public Long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public DateTime getCompletedOn() {
        return completedOn;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public ProjectState getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setCompletedOn(DateTime completedOn) {
        this.completedOn = completedOn;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public void setState(ProjectState state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String TEAM = "team";

        public static String NAME = "name";

        public static String STARTED = "createdOn";

        public static String COMPLETED = "completedOn";

        public static String DEADLINE = "deadline";

        public static String STATE = "state";

        public static String DESCRIPTION = "description";
    }
}
