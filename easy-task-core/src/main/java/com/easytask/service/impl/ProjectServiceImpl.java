package com.easytask.service.impl;

import com.easytask.model.jpa.Comment;
import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Task;
import com.easytask.persistence.IProjectRepository;
import com.easytask.persistence.IUserRepository;
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

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
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
}
