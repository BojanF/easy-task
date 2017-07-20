package com.easytask.persistence.impl;

import com.easytask.model.enums.State;
import com.easytask.model.jpa.*;
import com.easytask.persistence.IWorkerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marijo on 21-Jun-17.
 */
@Primary
@Repository
public class WorkerRepositoryImpl implements IWorkerRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    public List<Worker> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Worker> cq = cb.createQuery(Worker.class);
        Root<Worker> from = cq.from(Worker.class);
        return entityManager.createQuery(cq).getResultList();

    }

    @Transactional
    public Worker findById(Long id) {
        Worker worker = entityManager.find(Worker.class, id);
        if (worker != null) {
            entityManager.refresh(worker);
        }
        return worker;
    }

    @Transactional
    public Worker insert(Worker worker) {
        entityManager.persist(worker);
        entityManager.flush();
        return worker;
    }

    @Transactional
    public Worker update(Worker worker) {
        worker = entityManager.merge(worker);
        entityManager.flush();
        return worker;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public List<Document> getDocumentsByWorker(Long workerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> cq = cb.createQuery(Document.class);
        Root<Document> from= cq.from(Document.class);
        cq.where(
                cb.equal(
                        from.get(Document.FIELDS.WORKER),
                        workerId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<Comment> getCommentsByWorker(Long workerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> from= cq.from(Comment.class);
        cq.where(
                cb.equal(
                        from.get(Comment.FIELDS.WORKER),
                        workerId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<Project> getProjectsByWorker(Long workerId) {
        Set<Team> teams = new HashSet<Team>();
        for (Team t: findById(workerId).getTeams()) {
            teams.add(t);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);

        cq.where(
                from.get(Project.FIELDS.TEAM).in(
                        findById(workerId).getTeams()
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }


    public List<Project> getProjectsLeadByWorker(Long workerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);
        Join<Project,Team> team = from.join(Project.FIELDS.TEAM,JoinType.LEFT);
        Join<Team,Leader> leader = team.join(Team.FIELDS.LEADER,JoinType.LEFT);
        cq.where(
                cb.equal(
                        leader.get(Leader.FIELDS.WORKER).get("id"),
                        workerId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }
}

