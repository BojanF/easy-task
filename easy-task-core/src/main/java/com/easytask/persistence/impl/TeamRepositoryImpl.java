package com.easytask.persistence.impl;

import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;
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
    public Team insertTeamWorker(Team team, Worker worker) {
        team.addWorker(worker);
        return update(team);
    }

    @Transactional
    public Team removeTeamWorker(Team team, Worker worker) {
        team.removeWorker(worker);
        return update(team);
    }

    @Transactional
    public Team removeAllTeamWorkers(Long teamId) {
        Team team = entityManager.find(Team.class, teamId);
        Set<Worker> workers = team.getWorkers();
        workers.clear();
        team.setWorkers(workers);
        return update(team);
    }
}
