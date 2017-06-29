package com.easytask.persistence.impl;

import com.easytask.model.jpa.Project;
import com.easytask.persistence.IProjectRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */
@Primary
@Repository
public class ProjectRepositoryImpl implements IProjectRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    @Transactional
    public Project insert(Project project) {
        entityManager.persist(project);
        entityManager.flush();
        return project;
    }

    @Transactional
    public Project findById(Long id) {
        Project project = entityManager.find(Project.class, id);
        if(project != null){
            entityManager.refresh(project);
        }
        return project;
    }

    @Transactional
    public Project update(Project project) {
        project = entityManager.merge(project);
        entityManager.flush();
        return project;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));

    }

    public List<Project> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from = cq.from(Project.class);
        return entityManager.createQuery(cq).getResultList();
    }
}
