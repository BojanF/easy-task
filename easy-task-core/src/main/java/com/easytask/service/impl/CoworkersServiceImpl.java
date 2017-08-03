package com.easytask.service.impl;

import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.CoworkerId;
import com.easytask.model.jpa.User;
import com.easytask.persistence.ICoworkersRepository;
import com.easytask.service.ICoworkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Marijo on 30-Jul-17.
 */

@Service
public class CoworkersServiceImpl implements ICoworkersService {

    @Autowired
    ICoworkersRepository coworkerRepository;

    public List<Coworkers> findAll() {
        return coworkerRepository.findAll();
    }

    public Coworkers findById(CoworkerId id){
        return coworkerRepository.findById(id);
    }

    public Coworkers insert(Coworkers coworkers) {
        return coworkerRepository.insert(coworkers);
    }

    public Coworkers update(Coworkers coworkers) {
        return coworkerRepository.update(coworkers);
    }

    public void deleteById(CoworkerId id){
        coworkerRepository.deleteById(id);
    }

    public List<User> getCoworkersForUser(Long userId) {
        return coworkerRepository.getCoworkersForUser(userId);
    }

    public List<User> getCoworkerRequestsSent(Long userId) {
        return coworkerRepository.getCoworkerRequestsSent(userId);
    }

    public List<User> getCoworkerRequestsReceived(Long userId){
        return coworkerRepository.getCoworkerRequestsReceived(userId);
    };

    public List<User> getNonEngagedUsersForUser(Long userId) {
        return coworkerRepository.getNonEngagedUsersForUser(userId);
    }


}
