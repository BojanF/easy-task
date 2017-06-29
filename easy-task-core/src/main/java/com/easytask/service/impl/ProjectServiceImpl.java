package com.easytask.service.impl;

import com.easytask.model.jpa.Project;
import com.easytask.persistence.IProjectRepository;
import com.easytask.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */
@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    IProjectRepository projectCrudRepository;

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
}
