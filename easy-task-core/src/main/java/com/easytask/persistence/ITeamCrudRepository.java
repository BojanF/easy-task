package com.easytask.persistence;

import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;

import java.util.List;

/**
 * Created by Bojan on 6/24/2017.
 */
public interface ITeamCrudRepository {

    Team insert(Team team);

    Team findById(Long id);

    List<Team> findAll();

    Team update(Team team);

    void deleteById(Long id);

    void insertTeamWorker(Team team, Worker worker);

    void removeTeamWorker(Team team, Worker worker);

    void removeAllTeamWorkers(Long teamId);


}
