package com.easytask.service;

import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.CoworkerId;
import com.easytask.model.jpa.User;

import java.util.List;

/**
 * Created by Marijo on 30-Jul-17.
 */

public interface ICoworkersService {

    List<Coworkers> findAll();

    Coworkers findById(CoworkerId id);

    Coworkers insert(Coworkers coworkers);

    Coworkers update(Coworkers coworkers);

    void deleteById(CoworkerId id);

    List<User> getCoworkersForUser(Long userId);

    List<User> getCoworkerRequestsSent(Long userId);

    List<User> getCoworkerRequestsReceived(Long userId);

    List<User> getNonEngagedUsersForUser(Long userId);

    List<User> searchNonEngagedUsersForUser(Long userId, String searchCriteria);

    Coworkers acceptRequest(Coworkers coworkers);

    void removeAsCoworker(CoworkerId id);

    void refuseRequest(CoworkerId id);

}
