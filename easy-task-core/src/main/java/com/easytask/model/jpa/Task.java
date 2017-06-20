package com.easytask.model.jpa;

import javax.persistence.Entity;

import com.easytask.model.enums.State;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bojan on 6/7/2017.
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Leader leader;

    @ManyToOne
    private  Project project;

    @NotNull
    private String name;

    @NotNull
    private DateTime startedOn;

    private DateTime finishedOn;

    @NotNull
    private DateTime deadline;

    @NotNull
    private State taskState;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_worker",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )

    //getters
    private Set<Worker> workers = new HashSet<Worker>();

    public Integer getId() {
        return id;
    }

    public Leader getLeader() {
        return leader;
    }

    public Project getProject() {
        return project;
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

    public Set<Worker> getWorkers() {
        return workers;
    }

    public State getTaskState() {
        return taskState;
    }


    //setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public void setTaskState(State taskState) {
        this.taskState = taskState;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }
}
