package com.easytask.service;

import com.easytask.model.jpa.Worker;

import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */
public interface IWorkerService {

    List<Worker> findAll();

    Worker findById(Long id);

    Worker insert(Worker worker);

    void update(Worker worker);

    void deleteById(Long id);

}
