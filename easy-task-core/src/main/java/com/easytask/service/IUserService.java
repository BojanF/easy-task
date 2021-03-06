package com.easytask.service;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;

import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */

public interface IUserService {

    List<User> findAll();

    User findById(Long id);

    User insert(User user);

    User update(User user);

    void deleteById(Long id);

    List<Document> getDocumentsByUser(Long userId);

    List<Comment> getCommentsByUser(Long userId);

    List<Project> getProjectsByUser(Long userId);

    List<Team> getTeamsLeadByUser(Long userId);

    List<Project> getProjectsLeadByUser(Long userId);

    List<Team> getTeamsForUser(Long userId);

    List<Task> getTasksForUser(Long userId);

    //dopolnitelno dodadeni, bez unit testovi
    List<Task> getTasksForUserByState(Long userId, TaskState state);

    List<Project> getUrgentProjectsForUser(Long userId);

    List<Long> projectStatsLeader(Long userId);

    List<Long> tasksStatsLeader(Long userId);

    List<TeamLeader> getTeamsInfoLeadByUser(Long leaderId);

    List<TeamLeader> getTeamsInfoTeamsForUser(Long userId);

    List<Task> getUrgentTask(Long userId);

    List<Team> getTeamsMemberOf(Long userId);

    User getUserByUsername(String username);



}
