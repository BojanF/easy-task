package com.easytask.service;

import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.User;
import java.util.List;
import java.util.Set;

/**
 * Created by Marijo on 30-Jul-17.
 */

public interface ICoworkersService {

    List<Coworkers> findAll();

    Set<Coworkers> findCoworkersByUser(User user);

    Coworkers insert(Coworkers coworkers);

    Coworkers update(Coworkers coworkers);

    void deleteCoworkers(Long userId,Long coworkerId);
}
