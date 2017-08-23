package com.easytask.persistence.impl;

import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.persistence.IUserRepository;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
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
        List<Team> teamsList = getTeamsForUser(userId);
        for (Team t: teamsList) {
            teams.add(t);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);

        cq.where(
                from.get(Project.FIELDS.TEAM).in(
                        teamsList
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

    public List<Team> getTeamsForUser(Long userId){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Team> cq = cb.createQuery(Team.class);
        Root<Team> from = cq.from(Team.class);
        Join<Team, User> join = from.join(Team.FIELDS.USERS,JoinType.LEFT);
        cq.where(
                cb.equal(
                        join.get(User.FIELDS.ID),
                        userId
                )
        );

        return entityManager.createQuery(cq).getResultList();
    }

    public List<Task> getTasksForUser(Long userId){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> from = cq.from(Task.class);
        Join<Task, User> join = from.join(Task.FIELDS.USERS,JoinType.LEFT);
        cq.where(
                cb.equal(
                        join.get(User.FIELDS.ID),
                        userId
                )
        );

        return entityManager.createQuery(cq).getResultList();
    }

    public List<Task> getTasksForUserByState(Long userId, TaskState state){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> from = cq.from(Task.class);
        Join<Task, User> join = from.join(Task.FIELDS.USERS,JoinType.LEFT);
        cq.where(
                cb.equal(
                        join.get(User.FIELDS.ID),
                        userId
                ),
                cb.equal(from.get(Task.FIELDS.STATE),
                        state
                )
        );

        return entityManager.createQuery(cq).getResultList();

    }

    public List<Project> getUrgentProjectsForUser(Long userId){
        List<Team> teamsList = getTeamsForUser(userId);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);
        DateTime today = DateTime.now();

        cq.where(
                from.get(Project.FIELDS.TEAM).in(
                        teamsList
                ),
                //TODO razlika megju denesen datum i deadlin ako e <7 da e urgent project
//                TODO
                cb.or(
                        cb.equal(from.get(Project.FIELDS.STATE),
                                ProjectState.NOT_STARTED
                        ),
                        cb.equal(from.get(Project.FIELDS.STATE),
                                ProjectState.IN_PROGRESS
                        ),
                        cb.equal(from.get(Project.FIELDS.STATE),
                                ProjectState.BREACH_OF_DEADLINE
                        )
                )
        ).orderBy(cb.asc(from.get(Project.FIELDS.DEADLINE)));
        return entityManager.createQuery(cq).getResultList();
    }

    public List<Team> getTeamsLeadByUser(Long userId){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Team> cq = cb.createQuery(Team.class);
        Root<Team> from = cq.from(Team.class);
        Join<Team, Leader> join = from.join(Team.FIELDS.LEADER,JoinType.LEFT);
        cq.where(
                cb.equal(
                        join.get(Leader.FIELDS.USER).get(User.FIELDS.ID),
                        userId
                )
        );

        return entityManager.createQuery(cq).getResultList();
    };

    public List<Long> projectStatsLeader(Long userId){

        ProjectsStatesByLeader projectStats = entityManager.createQuery("SELECT projectStats FROM ProjectsStatesByLeader projectStats WHERE projectStats.id=:id", ProjectsStatesByLeader.class).setParameter("id", userId).getSingleResult();
        return projectStats.getStats();

    }

    public List<Long> tasksStatsLeader(Long userId){

        TasksStatesByLeader taskStats = entityManager.createQuery("SELECT taskStats FROM TasksStatesByLeader taskStats WHERE taskStats.id=:id", TasksStatesByLeader.class).setParameter("id", userId).getSingleResult();
        return taskStats.getStats();

    }

}

