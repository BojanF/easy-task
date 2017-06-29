package com.easytask.persistence;

import com.easytask.model.jpa.Worker;

import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */
public interface IWorkerRepository {

    List<Worker> findAll();

    Worker findById(Long id);

    Worker insert(Worker worker);

    Worker update(Worker worker);

    void deleteById(Long id);
    
    

}