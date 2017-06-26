package com.easytask.persistence.impl;

import com.easytask.model.jpa.Leader;
import com.easytask.persistence.ILeaderCrudRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Bojan on 6/22/2017.
 */
@Primary
@Repository
public class LeaderCrudRepositoryImpl implements ILeaderCrudRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    @Transactional
    public Leader insert(Leader leader) {
        entityManager.persist(leader);
        entityManager.flush();
        return leader;
        /*Leader l2;
        //entityManager.
        entityManager.refresh(leader.getWorker());
        l2 = entityManager.merge(leader);
        entityManager.flush();
        return l2;*/

    }

    public List<Leader> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Leader> cq = cb.createQuery(Leader.class);
        Root<Leader> c = cq.from(Leader.class);

        cq.select(c);
        TypedQuery<Leader> q = entityManager.createQuery(cq);
        return  q.getResultList();
    }

    @Transactional
    public Leader update(Leader leader) {

        leader = entityManager.merge(leader);
        entityManager.flush();

        return leader;


    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public Leader findById(Long id) {

        Leader leader = entityManager.find(Leader.class, id);
        if(leader != null){
            entityManager.refresh(leader);
        }
        return leader;

    }
}
