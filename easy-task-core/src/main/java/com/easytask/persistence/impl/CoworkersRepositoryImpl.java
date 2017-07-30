package com.easytask.persistence.impl;

import com.easytask.model.jpa.Coworkers;
import com.easytask.persistence.ICoworkersRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Marijo on 30-Jul-17.
 */

@Primary
@Repository
public class CoworkersRepositoryImpl implements ICoworkersRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    public List<Coworkers> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coworkers> cq = cb.createQuery(Coworkers.class);
        Root<Coworkers> from = cq.from(Coworkers.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public Coworkers insert(Coworkers coworkers) {
        entityManager.persist(coworkers);
        entityManager.flush();
        return coworkers;
    }

    @Transactional
    public Coworkers update(Coworkers coworkers) {
        coworkers = entityManager.merge(coworkers);
        entityManager.flush();
        return coworkers;
    }

    @Query("DELETE from coworkers where user_id=:userId and coworker_id=:coworkerId")
    public void deleteCoworkers(@Param("userId") Long userId, @Param("coworkerId") Long coworkerId) {}
}
