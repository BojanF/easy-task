package com.easytask.model.jpa;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bojan on 8/23/2017.
 */
@Entity
@Table(name = "projects_states_by_leader")
@Immutable
public class ProjectsStatesByLeader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private Long created;

    @Column(name = "not_started")
    private Long notStarted;

    @Column(name = "in_progress")
    private Long inProgress;

    @Column(name = "up_to_date")
    private Long upToDate;

    private Long finished;

    private Long deadline;

    private Long total;

    public List<Long> getStats(){
        if(total.equals(0l)){
            return new ArrayList<Long>();
        }
        else return Arrays.asList(created, notStarted, inProgress, upToDate, finished, deadline, total);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getNotStarted() {
        return notStarted;
    }

    public void setNotStarted(Long notStarted) {
        this.notStarted = notStarted;
    }

    public Long getInProgress() {
        return inProgress;
    }

    public void setInProgress(Long inProgress) {
        this.inProgress = inProgress;
    }

    public Long getUpToDate() {
        return upToDate;
    }

    public void setUpToDate(Long upToDate) {
        this.upToDate = upToDate;
    }

    public Long getFinished() {
        return finished;
    }

    public void setFinished(Long finished) {
        this.finished = finished;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
