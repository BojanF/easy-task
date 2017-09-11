package com.easytask.service;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.model.pojos.DocumentResponse;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */

public interface IProjectService {

    Project insert(Project project);

    Project findById(Long id);

    Project update(Project project);

    void deleteById(Long id);

    List<Project> findAll();

    List<Task> getAllTasksForProject(Long projectId);

    List<Document> getAllDocumentsForProject(Long projectId);

    List<Comment> getAllCommentsForProject(Long projectId);

    List<Task> getAllTasksForUserOnProject(Long projectId,Long userId);

    List<DocumentResponse> getAllDocumentsForProjectWithoutData(Long projectId);

    //novi bez test
//    List<TaskState> getAllTaskStatesForTasksOnProject(Long projectId);

    TasksByProject getTasksStatesByProject(Long projectId);

    int deleteAllTasksForProject(Long projectId);

    int deleteAllCommentsForProject(Long projectId);

    int deleteAllDocumentsForProject(Long projectId);

    List<Project> getBreachedProjects(DateTime now);

    List<Project> getUpToDateProjectsWithBreachedDeadline(DateTime now);

    List<Task> getDeadlineBreachedTasksForProject(Long projectId, DateTime now);
}
