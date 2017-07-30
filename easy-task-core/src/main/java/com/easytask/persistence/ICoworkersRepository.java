package com.easytask.persistence;

import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.User;

import java.util.List;

/**
 * Created by Marijo on 30-Jul-17.
 */
public interface ICoworkersRepository {

    List<Coworkers> findAll();

    Coworkers insert(Coworkers coworkers);

    Coworkers update(Coworkers coworkers);

    void deleteCoworkers(Long userId,Long coworkerId);

}
