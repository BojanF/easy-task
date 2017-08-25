package com.easytask.model.jpa;

import jdk.nashorn.internal.ir.annotations.Immutable;

import javax.persistence.*;

/**
 * Created by Bojan on 8/25/2017.
 */

@Entity
@Table(name = "team_leader")
@Immutable
public class TeamLeader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(name = "project_num")
    private String projectNum;

    @Column(name = "tasks_num")
    private String tasksNum;

    @Column(name = "users_num")
    private String usersNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getTasksNum() {
        return tasksNum;
    }

    public void setTasksNum(String tasksNum) {
        this.tasksNum = tasksNum;
    }

    public String getUsersNum() {
        return usersNum;
    }

    public void setUsersNum(String usersNum) {
        this.usersNum = usersNum;
    }
}
