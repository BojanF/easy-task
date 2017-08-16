package com.easytask.service;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.Comment;
import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Task;
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

    //novi bez test
    List<TaskState> getAllTaskStatesForTasksOnProject(Long projectId);

}
