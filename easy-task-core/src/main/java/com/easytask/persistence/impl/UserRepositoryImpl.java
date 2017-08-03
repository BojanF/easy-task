package com.easytask.persistence.impl;

import com.easytask.model.jpa.*;
import com.easytask.persistence.IUserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marijo on 21-Jun-17.
 */

@Primary
@Repository
public class UserRepositoryImpl implements IUserRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    public List<User> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> from = cq.from(User.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public User findById(Long id) {

//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery q = cb.createQuery(User.class);
//        Root o = q.from(User.class);
//        o.fetch(User.FIELDS.COWORKERS, JoinType.INNER);
//        o.fetch("coworkerOf", JoinType.INNER);
//        q.select(o).where(cb.equal(o.get(User.FIELDS.ID), id));
//        q.where(cb.equal(o.get(User.FIELDS.ID), id));
//
//        User user = (User)this.entityManager.createQuery(q).getSingleResult();
//
//        TypedQuery<User> query = entityManager.createQuery(q);
//        return query.getSingleResult();

        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.refresh(user);
        }
         return user;
    }

    @Transactional
    public User insert(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    @Transactional
    public User update(User user) {
        user = entityManager.merge(user);
        entityManager.flush();
        return user;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public List<Document> getDocumentsByUser(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> cq = cb.createQuery(Document.class);
        Root<Document> from= cq.from(Document.class);
        cq.where(
                cb.equal(
                        from.get(Document.FIELDS.USER),
                        userId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<Comment> getCommentsByUser(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> from= cq.from(Comment.class);
        cq.where(
                cb.equal(
                        from.get(Comment.FIELDS.USER),
                        userId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<Project> getProjectsByUser(Long userId) {
        Set<Team> teams = new HashSet<Team>();
        for (Team t: findById(userId).getTeams()) {
            teams.add(t);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);

        cq.where(
                from.get(Project.FIELDS.TEAM).in(
                        findById(userId).getTeams()
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }


    public List<Project> getProjectsLeadByUser(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);
        Join<Project,Team> team = from.join(Project.FIELDS.TEAM,JoinType.LEFT);
        Join<Team,Leader> leader = team.join(Team.FIELDS.LEADER,JoinType.LEFT);
        cq.where(
                cb.equal(
                        leader.get(Leader.FIELDS.USER).get(User.FIELDS.ID),
                        userId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }
}

