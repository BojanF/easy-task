package com.easytask.service.impl;

import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;
import com.easytask.persistence.ITeamRepository;
import com.easytask.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bojan on 6/24/2017.
 */
@Service
public class TeamServiceImpl implements ITeamService {
    
    @Autowired
    ITeamRepository teamCrudRepository;
    
    public Team insert(Team team) {
        return teamCrudRepository.insert(team);
    }

    public Team findById(Long id) {
        return teamCrudRepository.findById(id);
    }

    public List<Team> findAll() {
        return teamCrudRepository.findAll();
    }

    public Team update(Team team) {
        Team old = teamCrudRepository.findById(team.getId());
        if(old != null){
            team = teamCrudRepository.update(team);
        }

        return team;

    }

    public void deleteById(Long id) {
        Team team = teamCrudRepository.findById(id);
        if (team != null) {
            teamCrudRepository.deleteById(id);
        }
    }

    public Team insertTeamWorker(Team team, Worker worker) {
        return teamCrudRepository.insertTeamWorker(team, worker);
    }

    public Team removeTeamWorker(Team team, Worker worker) {
        return teamCrudRepository.removeTeamWorker(team, worker);
    }

    public Team removeAllTeamWorkers(Long teamId) {
        return teamCrudRepository.removeAllTeamWorkers(teamId);
    }
}
