package com.easytask.service;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import java.util.List;
import java.util.Map;

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

    //List<Team> getTeamsLeadByUser(Long userId);
    List<Integer> getTaskStatesForAllProjectsLedByUser(Long userId);

    List<Long> projectStatsLeader(Long userId);

    List<Long> tasksStatsLeader(Long userId);


}
