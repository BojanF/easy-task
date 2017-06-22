package com.easytask.persistence.impl;

import com.easytask.model.jpa.Worker;
import com.easytask.persistence.WorkerCrudRepository;
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
 * Created by Marijo on 21-Jun-17.
 */
@Primary
@Repository
public class WorkerCrudRepositoryImpl implements WorkerCrudRepository {

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
        worker = entityManager.merge(worker);
        entityManager.flush();
        return worker;
    }

    @Transactional
    public void update(Worker worker) {
        entityManager.merge(worker);
        entityManager.flush();
    }
    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
