package com.easytask.model.jpa;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bojan on 8/24/2017.
 */
@Entity
@Table(name = "tasks_by_project")
@Immutable
public class TasksByProject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    @Column(name = "not_started")
    private Long notStarted;

    @Column(name = "in_progress")
    private Long inProgress;

    private Long finished;

    private Long breach;

    private Long total;

    public List<Long> getStats(){
        if(total.equals(0l)){
            return new ArrayList<Long>();
        }
        else return Arrays.asList(notStarted, inProgress, finished, breach, total);
    }

    public Long getId() {
        return id;
    }

    public Long getNotStarted() {
        return notStarted;
    }

    public Long getInProgress() {
        return inProgress;
    }

    public Long getFinished() {
        return finished;
    }

    public Long getBreach() {
        return breach;
    }

    public Long getTotal() {
        return total;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNotStarted(Long notStarted) {
        this.notStarted = notStarted;
    }

    public void setInProgress(Long inProgress) {
        this.inProgress = inProgress;
    }

    public void setFinished(Long finished) {
        this.finished = finished;
    }

    public void setBreach(Long breach) {
        this.breach = breach;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
