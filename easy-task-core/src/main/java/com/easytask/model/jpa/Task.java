package com.easytask.model.jpa;

import javax.persistence.Entity;

import com.easytask.model.enums.State;
import org.hibernate.annotations.Type;
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
    private Long id;

    @NotNull
    private String name;

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
    private State taskState;

    @ManyToOne
    private Leader givenByLeader;

    @ManyToOne
    private  Project onProject;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_worker",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )

    //getters
    private Set<Worker> workers = new HashSet<Worker>();

    public Long getId() {
        return id;
    }

    public Leader getLeader() {
        return givenByLeader;
    }

    public Project getProject() {
        return onProject;
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

    public String getDescription() {
        return description;
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLeader(Leader leader) {
        this.givenByLeader = leader;
    }

    public void setProject(Project project) {
        this.onProject = project;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void addWorker(Worker worker){
        this.workers.add(worker);
    }

    public void removeWorker(Worker worker){
        Set<Worker> workers = getWorkers();
        for(Worker w : workers){
            if(w.getId().equals(worker.getId())){
                this.workers.remove(w);
                break;
            }
        }
    }

    public static class FIELDS {
        public static String PROJECT = "onProject";
    }
}
