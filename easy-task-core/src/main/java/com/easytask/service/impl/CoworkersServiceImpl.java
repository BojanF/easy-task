package com.easytask.service.impl;

import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.User;
import com.easytask.persistence.ICoworkersRepository;
import com.easytask.service.ICoworkersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

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

    public Set<Coworkers> findCoworkersByUser(User user) {
        return user.getCoworkers();
    }

    public Coworkers insert(Coworkers coworkers) {
        return coworkerRepository.insert(coworkers);
    }

    public Coworkers update(Coworkers coworkers) {
        return coworkerRepository.update(coworkers);
    }

    public void deleteCoworkers(Long userId,Long coworkerId) {
        coworkerRepository.deleteCoworkers(userId,coworkerId);
    }
}
