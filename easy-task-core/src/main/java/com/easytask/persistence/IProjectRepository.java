package com.easytask.persistence;

import com.easytask.model.jpa.Project;

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

}
