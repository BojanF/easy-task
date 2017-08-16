package com.easytask.persistence;

import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.CoworkerId;
import com.easytask.model.jpa.User;

import java.util.List;

/**
 * Created by Marijo on 30-Jul-17.
 */
public interface ICoworkersRepository {

    List<Coworkers> findAll();

    Coworkers insertPair(Coworkers coworkers);

    Coworkers insert(Coworkers coworkers);

    Coworkers findById(CoworkerId id);

    Coworkers updatePair(Coworkers coworkers);

    Coworkers update(Coworkers coworkers);

    void deletePairById(CoworkerId id);

    void deleteById(CoworkerId id);

    List<User> getCoworkersForUser(Long userId);

    List<Long> getCoworkersForUserIdentifiers(Long userId);

    List<User> getCoworkerRequestsSent(Long userId);

    List<Long> getCoworkerRequestsSentIdentifiers(Long userId);

    List<User> getCoworkerRequestsReceived(Long userId);

    List<Long> getCoworkerRequestsReceivedIdentifiers(Long userId);

    List<User> getNonEngagedUsersForUser(Long userId);

}
