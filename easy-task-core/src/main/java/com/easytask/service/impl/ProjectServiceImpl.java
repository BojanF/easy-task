package com.easytask.service.impl;

import com.easytask.model.jpa.Comment;
import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Task;
import com.easytask.persistence.IProjectRepository;
import com.easytask.persistence.IWorkerRepository;
import com.easytask.service.IProjectService;
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
    IProjectRepository projectCrudRepository;

    @Autowired
    IWorkerRepository workerRepository;

    public Project insert(Project project) {
        return projectCrudRepository.insert(project);
    }

    public Project findById(Long id) {
        return projectCrudRepository.findById(id);
    }

    public Project update(Project project) {
        return projectCrudRepository.update(project);
    }

    public void deleteById(Long id) {
        projectCrudRepository.deleteById(id);
    }

    public List<Project> findAll() {
        return projectCrudRepository.findAll();
    }

    public List<Task> getAllTasksForProject(Long projectId) {
        return projectCrudRepository.getAllTasksForProject(projectId);
    }

    public List<Document> getAllDocumentsForProject(Long projectId) {
        return projectCrudRepository.getAllDocumentsForProject(projectId);
    }

    public List<Comment> getAllCommentsForProject(Long projectId) {
        return projectCrudRepository.getAllCommentsForProject(projectId);
    }

    public List<Task> getAllTasksForWorkerOnProject(Long projectId, Long workerId) {
        List<Task> tasks = new ArrayList<Task>();
        for(Task t : workerRepository.findById(workerId).getTasks()){
            if (t.getProject().getId() == projectId)
                tasks.add(t);
        }
        return tasks;
    }
}
