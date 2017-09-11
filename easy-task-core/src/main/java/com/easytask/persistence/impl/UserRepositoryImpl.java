package com.easytask.persistence.impl;

import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;

import com.easytask.persistence.IUserRepository;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
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
        if(teamsList.size()==0){
            return null;}
        else {
        for (Team t: teamsList) {
            teams.add(t);
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);

        cq.where(
                from.get(Project.FIELDS.TEAM).in(
                        teamsList
                ),
//                TODO da se vidi dali vaka e OK, dolniov uslov !!!
                cb.notEqual(
                    from.get(Project.FIELDS.TEAM).get(Team.FIELDS.LEADER).get(Leader.FIELDS.USER).get(User.FIELDS.ID),
                    userId
                )
        );
        return entityManager.createQuery(cq).getResultList();
        }
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

        //teams where user is member
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Team> cq = cb.createQuery(Team.class);
        Root<Team> from = cq.from(Team.class);
        Join<Team, User> join = from.join(Team.FIELDS.USERS,JoinType.LEFT);
        cq.where(
                cb.equal(
                        join.get(User.FIELDS.ID),
                        userId
                )/*,
                cb.notEqual(from.get(Team.FIELDS.LEADER).get(Leader.FIELDS.USER).get(User.FIELDS.ID),
                        userId
                )*/
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
        //only for active projects
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> from = cq.from(Task.class);
        Join<Task, User> join = from.join(Task.FIELDS.USERS,JoinType.LEFT);
        cq.where(
                cb.equal(
                        join.get(User.FIELDS.ID),
                        userId
                ),
                cb.or(
                        cb.equal(from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.STATE), ProjectState.NOT_STARTED),
                        cb.equal(from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.STATE), ProjectState.IN_PROGRESS),
                        cb.equal(from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.STATE), ProjectState.UP_TO_DATE),
                        cb.equal(from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.STATE), ProjectState.BREACH_OF_DEADLINE)
                ),
                cb.equal(from.get(Task.FIELDS.STATE),
                        state
                )
        );

        return entityManager.createQuery(cq).getResultList();

    }

    public List<Project> getUrgentProjectsForUser(Long userId){

        List<Team> teamsList = getTeamsForUser(userId);
        if(teamsList.size()==0) return null;
        else {
        TypedQuery<Project> urgentProjects = entityManager.createQuery("select p\n" +
                "from Project p\n" +
                "where\n" +
                "p.team IN (:teamsList) and\n" +
                "( datediff(p.deadline, now())<=7 or now()>p.deadline ) and\n" +
                "( p.state = 'CREATED' or\n" +
                "  p.state = 'NOT_STARTED' or\n" +
                "  p.state = 'IN_PROGRESS' or\n" +
                "  p.state = 'BREACH_OF_DEADLINE')", Project.class);
        urgentProjects.setParameter("teamsList", teamsList);
        List<Project> queryResults = urgentProjects.getResultList();
        return queryResults;}
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

    public List<TeamLeader> getTeamsInfoLeadByUser(Long userId){

        TypedQuery<TeamLeader> teamsInfo = entityManager.createQuery("select tl\n" +
                "from User u, Leader l, Team t, TeamLeader tl\n" +
                "where\n" +
                "u.id = :userId and\n" +
                "u.id = l.user.id and\n" +
                "l.id = t.leader.id and\n" +
                "t.id = tl.id", TeamLeader.class);
        teamsInfo.setParameter("userId", userId);
        return teamsInfo.getResultList();
    }

    public List<TeamLeader> getTeamsInfoTeamsForUser(Long userId){
        List<Team> helperList = getTeamsMemberOf(userId);
        if(helperList.size()==0){
            return null;
        }
        else{
        TypedQuery<TeamLeader> teamsInfo = entityManager.createQuery("select tl\n" +
                "from Team t, TeamLeader tl\n" +
                "where\n" +
                "t in (:teams) and\n" +
                "t.id = tl.id", TeamLeader.class);
        teamsInfo.setParameter("teams", helperList);
        return teamsInfo.getResultList();}
    }

    @Transactional
    public List<Task> getUrgentTask(Long userId){



//        User user = findById(userId);
//
//        TypedQuery<Task> urgentTasks = entityManager.createQuery("select t from Task t where :user in (t.users) and t.state!='FINISHED' and ( datediff(t.deadline, now())<=7 or now()>t.deadline) ", Task.class);
//
//        urgentTasks.setParameter("user", user);
//        return urgentTasks.getResultList();

        return new ArrayList<Task>();

    }

    public List<Team> getTeamsMemberOf(Long userId){

        //teams where user is member but is not leader

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Team> cq = cb.createQuery(Team.class);
        Root<Team> from = cq.from(Team.class);
        Join<Team, User> join = from.join(Team.FIELDS.USERS,JoinType.LEFT);
        cq.where(
                cb.equal(
                        join.get(User.FIELDS.ID),
                        userId
                ),
                cb.notEqual(from.get(Team.FIELDS.LEADER).get(Leader.FIELDS.USER).get(User.FIELDS.ID),
                        userId
                )
        );

        return entityManager.createQuery(cq).getResultList();
    }

    public User getUserByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> from= cq.from(User.class);
        cq.where(
                cb.equal(
                        from.get(User.FIELDS.USERNAME),
                        username
                )
        );
        return entityManager.createQuery(cq).getSingleResult();
    }







}

