package com.easytask.service.impl;

import com.easytask.model.jpa.Project;
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
    ITeamRepository teamRepository;
    
    public Team insert(Team team) {
        return teamRepository.insert(team);
    }

    public Team findById(Long id) {
        return teamRepository.findById(id);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team update(Team team) {
        Team old = teamRepository.findById(team.getId());
        if(old != null){
            team = teamRepository.update(team);
        }

        return team;

    }

    public void deleteById(Long id) {
        Team team = teamRepository.findById(id);
        if (team != null) {
            teamRepository.deleteById(id);
        }
    }

    public Team insertTeamWorker(Team team, Worker worker) {
        return teamRepository.insertTeamWorker(team, worker);
    }

    public Team removeTeamWorker(Team team, Worker worker) {
        return teamRepository.removeTeamWorker(team, worker);
    }

    public Team removeAllTeamWorkers(Long teamId) {
        return teamRepository.removeAllTeamWorkers(teamId);
    }

    public List<Project> getAllProjectsByTeam(Long teamId) {
        return teamRepository.getAllProjectsByTeam(teamId);
    }
}
