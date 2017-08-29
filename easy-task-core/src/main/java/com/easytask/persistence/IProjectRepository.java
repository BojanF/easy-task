package com.easytask.persistence;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.model.pojos.DocumentResponse;

import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */

public interface IProjectRepository {

    Project insert(Project project);

    Project findById(Long id);

    Project update(Project project);

    void deleteById(Long id);

    List<Project> findAll();

    List<Task> getAllTasksForProject(Long projectId);

    List<Document> getAllDocumentsForProject(Long projectId);

    List<DocumentResponse> getAllDocumentsForProjectWithoutData(Long projectId);

    List<Comment> getAllCommentsForProject(Long projectId);

    //novi bez test
//    List<TaskState> getAllTaskStatesForTasksOnProject(Long projectId);

    TasksByProject getTasksStatesByProject(Long projectId);

    int deleteAllTasksForProject(Long projectId);

    int deleteAllCommentsForProject(Long projectId);

    int deleteAllDocumentsForProject(Long projectId);

    Integer getNumberOfCommentsForProject(Long projectId);

    Integer getNumberOfDocumentsForProject(Long projectId);

    Integer getNumberOfTasksForProject(Long projectId);


}
