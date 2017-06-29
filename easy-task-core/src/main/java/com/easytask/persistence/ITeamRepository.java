package com.easytask.persistence;

import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;

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

    Team insertTeamWorker(Team team, Worker worker);

    Team removeTeamWorker(Team team, Worker worker);

    Team removeAllTeamWorkers(Long teamId);


}