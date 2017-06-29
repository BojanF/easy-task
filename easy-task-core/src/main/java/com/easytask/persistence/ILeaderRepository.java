package com.easytask.persistence;

import com.easytask.model.jpa.Leader;

import java.util.List;

/**
 * Created by Bojan on 6/22/2017.
 */
public interface ILeaderRepository {

    Leader insert (Leader leader);

    List<Leader> findAll();

    Leader update (Leader leader);

    void deleteById(Long id);

    Leader findById(Long id);


}
