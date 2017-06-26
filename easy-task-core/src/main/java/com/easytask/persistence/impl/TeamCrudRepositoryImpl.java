package com.easytask.persistence.impl;

import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;
import com.easytask.persistence.ITeamCrudRepository;

import com.easytask.service.ITeamService;
import com.easytask.service.IWorkerService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
public class TeamCrudRepositoryImpl implements ITeamCrudRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    /*@Autowired
    ITeamService teamService;*/

    @Autowired
    IWorkerService workerService;

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
        Root<Team> c = cq.from(Team.class);

        cq.select(c);
        TypedQuery<Team> q = entityManager.createQuery(cq);
        return  q.getResultList();
    }

    @Transactional
    public Team update(Team team) {
        team = entityManager.merge(team);
        entityManager.flush();

        return team;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public void insertTeamWorker(Team team, Worker worker) {
        team.addWorker(worker);
        update(team);

        worker.addTeam(team);
        workerService.update(worker);

    }

    @Transactional
    public void removeTeamWorker(Team team, Worker worker) {
        team.removeWorker(worker);
        update(team);

        worker.removeTeam(team);
        workerService.update(worker);

    }

    @Transactional
    public void removeAllTeamWorkers(Long teamId) {
        Team team = entityManager.find(Team.class, teamId);
        Set<Worker> workers = team.getWorkers();
        workers.clear();
        team.setWorkers(workers);
        update(team);
    }
}
