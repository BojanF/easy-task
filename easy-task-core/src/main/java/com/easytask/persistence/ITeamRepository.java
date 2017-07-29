package com.easytask.persistence;

import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.User;
import java.util.List;

/**
 * Created by Bojan on 6/24/2017.
 */

public interface ITeamRepository {

    Team insert(Team team);

    Team findById(Long id);

    List<Team> findAll();

    Team update(Team team);

    void deleteById(Long id);

    Team insertTeamUser(Team team, User user);

    Team removeTeamUser(Team team, User user);

    Team removeAllTeamUsers(Long teamId);

    List<Project> getAllProjectsByTeam(Long teamId);
}
