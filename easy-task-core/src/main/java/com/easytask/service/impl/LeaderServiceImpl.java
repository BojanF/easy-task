package com.easytask.service.impl;

import com.easytask.model.jpa.Leader;
import com.easytask.persistence.ILeaderRepository;
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
    ILeaderRepository leaderRepository;

    public Leader insert(Leader leader) {
        return leaderRepository.insert(leader);
    }

    public List<Leader> findAll() {
        return leaderRepository.findAll();
    }

    public Leader update(Leader leader) {
        Leader old = leaderRepository.findById(leader.getId());
        if(old != null){
            leader = leaderRepository.update(leader);
        }
        return leader;
    }

    public void deleteById(Long id) {
        Leader leader = leaderRepository.findById(id);
        if(leader != null)
            leaderRepository.deleteById(id);
    }

    public Leader findById(Long id) {
        return leaderRepository.findById(id);
    }
}
