package com.easytask.service.impl;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.model.pojos.DocumentResponse;
import com.easytask.persistence.IDocumentRepository;
import com.easytask.persistence.IProjectRepository;
import com.easytask.persistence.ITaskRepository;
import com.easytask.persistence.IUserRepository;
import com.easytask.service.ICommentService;
import com.easytask.service.IDocumentService;
import com.easytask.service.IProjectService;
import com.easytask.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */

@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    IProjectRepository projectRepository;

    @Autowired
    IUserRepository userRepository;

    public Project insert(Project project) {
        return projectRepository.insert(project);
    }

    public Project findById(Long id) {
        return projectRepository.findById(id);
    }

    public Project update(Project project) {
        return projectRepository.update(project);
    }

    public void deleteById(Long projectId) {
        int tasksSize = projectRepository.getNumberOfTasksForProject(projectId);
        int documentsSize = projectRepository.getNumberOfDocumentsForProject(projectId);
        int commentsSize = projectRepository.getNumberOfCommentsForProject(projectId);

        int deleteTasks = deleteAllTasksForProject(projectId);
        int deleteDocuments = deleteAllDocumentsForProject(projectId);
        int deleteComments = deleteAllCommentsForProject(projectId);

        if(tasksSize==deleteTasks &&
           documentsSize==deleteDocuments &&
           commentsSize==deleteComments) {

           projectRepository.deleteById(projectId);
        }
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public List<Task> getAllTasksForProject(Long projectId) {
        return projectRepository.getAllTasksForProject(projectId);
    }

    public List<Document> getAllDocumentsForProject(Long projectId) {
        return projectRepository.getAllDocumentsForProject(projectId);
    }

    public List<DocumentResponse> getAllDocumentsForProjectWithoutData(Long projectId){
        return projectRepository.getAllDocumentsForProjectWithoutData(projectId);
    }

    public List<Comment> getAllCommentsForProject(Long projectId) {
        return projectRepository.getAllCommentsForProject(projectId);
    }

    public List<Task> getAllTasksForUserOnProject(Long projectId, Long userId) {

        List<Task> tasks = new ArrayList<Task>();
        for(Task t : userRepository.getTasksForUser(userId)){
            if (t.getProject().getId() == projectId)
                tasks.add(t);
        }
        return tasks;

    }

//    public List<TaskState> getAllTaskStatesForTasksOnProject(Long projectId){
//        return projectRepository.getAllTaskStatesForTasksOnProject(projectId);
//    }

    public TasksByProject getTasksStatesByProject(Long projectId){
        return projectRepository.getTasksStatesByProject(projectId);
    }

    public int deleteAllTasksForProject(Long projectId){
        return projectRepository.deleteAllTasksForProject(projectId);
    };

    public int deleteAllCommentsForProject(Long projectId){
        return projectRepository.deleteAllCommentsForProject(projectId);
    };

    public int deleteAllDocumentsForProject(Long projectId){
        return projectRepository.deleteAllDocumentsForProject(projectId);
    };
}
