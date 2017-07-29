package com.easytask.service.impl;

import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.User;
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

    public Team insertTeamUser(Team team, User user) {
        return teamRepository.insertTeamUser(team, user);
    }

    public Team removeTeamUser(Team team, User user) {
        return teamRepository.removeTeamUser(team, user);
    }

    public Team removeAllTeamUsers(Long teamId) {
        return teamRepository.removeAllTeamUsers(teamId);
    }

    public List<Project> getAllProjectsByTeam(Long teamId) {
        return teamRepository.getAllProjectsByTeam(teamId);
    }
}
