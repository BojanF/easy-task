package com.easytask.persistence.impl;

import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.User;
import com.easytask.persistence.ITeamRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * Created by Bojan on 6/24/2017.
 */

@Primary
@Repository
public class TeamRepositoryImpl implements ITeamRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    UserRepositoryImpl userRepository;

    @Transactional
    public Team insert(Team team) {
        entityManager.persist(team);
        entityManager.flush();
        return team;
    }

    @Transactional
    public Team findById(Long id) {
        Team leader = entityManager.find(Team.class, id);
        if(leader != null){
            entityManager.refresh(leader);
        }
        return leader;
    }

    public List<Team> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Team> cq = cb.createQuery(Team.class);
        Root<Team> from = cq.from(Team.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public Team update(Team team) {
        team=entityManager.merge(team);
        entityManager.flush();
        return team;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public Team insertTeamUser(Team team, User user) {
        team.addUser(user);
        return update(team);
    }

    @Transactional
    public Team removeTeamUser(Team team, User user) {
        team.removeUser(user);
        return update(team);
    }

    @Transactional
    public Team removeAllTeamUsers(Long teamId) {
        Team team = entityManager.find(Team.class, teamId);
        Set<User> users = team.getUsers();
        users.clear();
        team.setUsers(users);
        return update(team);
    }

    @Transactional
    public List<Project> getAllProjectsByTeam(Long teamId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from= cq.from(Project.class);
        cq.where(
                cb.equal(
                        from.get(Project.FIELDS.TEAM).get(Team.FIELDS.ID),
                        teamId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }
}
