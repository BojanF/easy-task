package com.easytask.service.impl;

import com.easytask.model.jpa.Leader;
import com.easytask.persistence.ILeaderCrudRepository;
import com.easytask.service.ILeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bojan on 6/22/2017.
 */
@Service
public class LeaderServiceImpl implements ILeaderService {

    @Autowired
    ILeaderCrudRepository leaderCrudRepository;

    public Leader insert(Leader leader) {
        return leaderCrudRepository.insert(leader);
    }

    public List<Leader> findAll() {
        return leaderCrudRepository.findAll();
    }

    public Leader update(Leader leader) {
        Leader old = leaderCrudRepository.findById(leader.getId());
        if(old != null){
            leader = leaderCrudRepository.update(leader);
        }
        return leader;
    }

    public void deleteById(Long id) {
        Leader leader = leaderCrudRepository.findById(id);
        if(leader != null)
            leaderCrudRepository.deleteById(id);
    }

    public Leader findById(Long id) {
        return leaderCrudRepository.findById(id);
    }
}
