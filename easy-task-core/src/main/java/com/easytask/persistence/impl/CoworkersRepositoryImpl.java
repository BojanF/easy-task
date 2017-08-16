package com.easytask.persistence.impl;

import com.easytask.model.enums.CoworkerState;
import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.CoworkerId;
import com.easytask.model.jpa.User;
import com.easytask.persistence.ICoworkersRepository;
import com.easytask.service.IUserService;
import org.hibernate.metamodel.relational.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
    public Coworkers findById(CoworkerId id) {
        Coworkers coworkers = entityManager.find(Coworkers.class, id);
        if(coworkers != null){
            entityManager.refresh(coworkers);
        }
        return coworkers;
    }

    @Transactional
    public Coworkers insertPair(Coworkers coworkers){
        Coworkers otherWay = new Coworkers();
        otherWay.setId(new CoworkerId(coworkers.getUserB().getId(), coworkers.getUserA().getId()));
        otherWay.setUserA(coworkers.getUserB());
        otherWay.setUserB(coworkers.getUserA());
        otherWay.setState(coworkers.getState());
        otherWay = insert(otherWay);

        return insert(coworkers);
    }

    @Transactional
    public Coworkers insert(Coworkers coworkers) {
        entityManager.merge(coworkers);
        entityManager.flush();
        return coworkers;
    }

    @Transactional
    public Coworkers updatePair(Coworkers coworkers){
        CoworkerId otherWayId = new CoworkerId(coworkers.getUserB().getId(), coworkers.getUserA().getId());
        Coworkers otherWay = findById(otherWayId);
        otherWay = update(otherWay);

        return update(coworkers);

    }

    @Transactional
    public Coworkers update(Coworkers coworkers) {
        coworkers = entityManager.merge(coworkers);
        entityManager.flush();
        return coworkers;
    }

    @Transactional
    public void deletePairById(CoworkerId id){
        CoworkerId otherWay = new CoworkerId(id.getUserB(), id.getUserA());

        deleteById(otherWay);
        deleteById(id);
    }

    @Transactional
    public void deleteById(CoworkerId id){
        entityManager.remove(findById(id));
    }

    public List<User> getCoworkersForUser(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coworkers> cq = cb.createQuery(Coworkers.class);
        Root<Coworkers> from = cq.from(Coworkers.class);

        cq.select(from).where(
                cb.or(
                    cb.equal(from.get(Coworkers.FIELDS.USER_A).get(User.FIELDS.ID),
                            userId),
                    cb.equal(from.get(Coworkers.FIELDS.USER_B).get(User.FIELDS.ID),
                            userId)
                ),
                cb.equal(from.get(Coworkers.FIELDS.STATE),
                        CoworkerState.ACCEPTED)
        );
        List<Coworkers> acceptedRequestCoworkersList = entityManager.createQuery(cq).getResultList();

        List<User> resultList = new ArrayList<User>();
        for(Coworkers cw : acceptedRequestCoworkersList){
            if(cw.getUserA().getId().equals(userId))
                resultList.add(cw.getUserB());
            else{
                resultList.add(cw.getUserA());
            }
        }

        return resultList;
    }

    //TODO da se smisli podeskriptivno ime za funcijava
    public List<Long> getCoworkersForUserIdentifiers(Long userId) {
        //fetches only identifiers fo users that are coworkers for given user(userId)
        List<Long> resultListIdentifiers = new ArrayList<Long>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> cqFirstHalf = cb.createQuery(Long.class);
        Root<Coworkers> fromFirstHalf = cqFirstHalf.from(Coworkers.class);

        cqFirstHalf.select(fromFirstHalf.get(Coworkers.FIELDS.USER_B).get(User.FIELDS.ID).as(Long.class)).where(

                cb.equal(fromFirstHalf.get(Coworkers.FIELDS.USER_A).get(User.FIELDS.ID),
                        userId),

                cb.equal(fromFirstHalf.get(Coworkers.FIELDS.STATE),
                        CoworkerState.ACCEPTED)
        );
        resultListIdentifiers.addAll(entityManager.createQuery(cqFirstHalf).getResultList());


        CriteriaQuery<Long> cqSecondHalf = cb.createQuery(Long.class);
        Root<Coworkers> fromSecondHalf = cqSecondHalf.from(Coworkers.class);
        cqSecondHalf.select(fromSecondHalf.get(Coworkers.FIELDS.USER_A).get(User.FIELDS.ID).as(Long.class)).where(

                cb.equal(fromSecondHalf.get(Coworkers.FIELDS.USER_B).get(User.FIELDS.ID),
                        userId),

                cb.equal(fromSecondHalf.get(Coworkers.FIELDS.STATE),
                        CoworkerState.ACCEPTED)
        );
        resultListIdentifiers.addAll(entityManager.createQuery(cqSecondHalf).getResultList());


        return resultListIdentifiers;
    }

    //TODO da se smisli podeskriptivno ime za funcijava
    public List<User> getCoworkerRequestsSent(Long userId) {
        //fetches requests that user sent
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coworkers> cq = cb.createQuery(Coworkers.class);
        Root<Coworkers> from = cq.from(Coworkers.class);

        cq.select(from).where(
                cb.equal(from.get(Coworkers.FIELDS.USER_A).get(User.FIELDS.ID),
                        userId),
                cb.equal(from.get(Coworkers.FIELDS.STATE),
                        CoworkerState.PENDING)
        );
        List<Coworkers> pendingRequestCoworkersList = entityManager.createQuery(cq).getResultList();

        List<User> resultList = new ArrayList<User>();
        for(Coworkers cw : pendingRequestCoworkersList){
            resultList.add(cw.getUserB());
        }

        return resultList;
    }

    //TODO da se smisli podeskriptivno ime za funcijava
    public List<Long> getCoworkerRequestsSentIdentifiers(Long userId) {
        //same function as getCoworkerRequestsSent
        //but fetches only identifiers for users instead entire user object
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Coworkers> from = cq.from(Coworkers.class);

        cq.select(from.get(Coworkers.FIELDS.USER_B).get(User.FIELDS.ID).as(Long.class)).where(
                cb.equal(from.get(Coworkers.FIELDS.USER_A).get(User.FIELDS.ID),
                        userId),
                cb.equal(from.get(Coworkers.FIELDS.STATE),
                        CoworkerState.PENDING)
        );
        return entityManager.createQuery(cq).getResultList();

    }

    //TODO da se smisli podeskriptivno ime za funcijava
    public List<User> getCoworkerRequestsReceived(Long userId) {
        //fetches requests that user received
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Coworkers> cq = cb.createQuery(Coworkers.class);
        Root<Coworkers> from = cq.from(Coworkers.class);

        cq.select(from).where(
                cb.equal(from.get(Coworkers.FIELDS.USER_B).get(User.FIELDS.ID),
                        userId),
                cb.equal(from.get(Coworkers.FIELDS.STATE),
                        CoworkerState.PENDING)
        );
        List<Coworkers> pendingRequestCoworkersList = entityManager.createQuery(cq).getResultList();

        List<User> resultList = new ArrayList<User>();
        for(Coworkers cw : pendingRequestCoworkersList){
            resultList.add(cw.getUserA());
        }

        return resultList;
    }

    //TODO da se smisli podeskriptivno ime za funcijava
    public List<Long> getCoworkerRequestsReceivedIdentifiers(Long userId) {
        //same function as getCoworkerRequestsReceived
        //but fetches only identifiers for users instead entire user object
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Coworkers> from = cq.from(Coworkers.class);
        cq.select(from.get(Coworkers.FIELDS.USER_A).get(User.FIELDS.ID).as(Long.class)).where(
                cb.equal(from.get(Coworkers.FIELDS.USER_B).get(User.FIELDS.ID),
                        userId),
                cb.equal(from.get(Coworkers.FIELDS.STATE),
                        CoworkerState.PENDING)
        );
        return entityManager.createQuery(cq).getResultList();

    }

    public List<User> getNonEngagedUsersForUser(Long userId) {

        List<Long> engagedUsersIdentifiers = new ArrayList<Long>();
        List<Long> acceptedI = getCoworkersForUserIdentifiers(userId);
        List<Long> sentRequestsI = getCoworkerRequestsSentIdentifiers(userId);
        List<Long> receivedRequestsI = getCoworkerRequestsReceivedIdentifiers(userId);

        //user can`t send coworker request to himself
        engagedUsersIdentifiers.add(userId);
        engagedUsersIdentifiers.addAll(acceptedI);
        engagedUsersIdentifiers.addAll(sentRequestsI);
        engagedUsersIdentifiers.addAll(receivedRequestsI);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> from= cq.from(User.class);
        cq.select(from).where(
                from.get(User.FIELDS.ID).in(engagedUsersIdentifiers).not()
        );

        return entityManager.createQuery(cq).getResultList();
    }

}
