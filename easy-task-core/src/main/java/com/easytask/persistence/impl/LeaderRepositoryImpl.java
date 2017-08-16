package com.easytask.persistence.impl;

import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.User;
import com.easytask.persistence.ILeaderRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Bojan on 6/22/2017.
 */
@Primary
@Repository
public class LeaderRepositoryImpl implements ILeaderRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    @Transactional
    public Leader insert(Leader leader) {
        entityManager.persist(leader);
        entityManager.flush();
        return leader;
    }

    public List<Leader> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Leader> cq = cb.createQuery(Leader.class);
        Root<Leader> from = cq.from(Leader.class);
        return entityManager.createQuery(cq).getResultList();
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

    @Transactional
    public Leader getLeaderForUserId(Long userId){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Leader> cq = cb.createQuery(Leader.class);
        Root<Leader> from = cq.from(Leader.class);

        cq.select(from).where(
                cb.equal(from.get(Leader.FIELDS.USER).get(User.FIELDS.ID),
                        userId
                )

        );
        List<Leader> u = entityManager.createQuery(cq).getResultList();
        if(u.size() == 0)
            return null;
        else return u.get(0);
//        int x = 0;
//        return u;
//        Leader result = entityManager.createQuery(cq).getSingleResult();
//        return result.getUser();
    }
}
