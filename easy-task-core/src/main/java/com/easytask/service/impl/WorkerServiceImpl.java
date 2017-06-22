package com.easytask.service.impl;

import com.easytask.model.jpa.Worker;
import com.easytask.persistence.WorkerCrudRepository;
import com.easytask.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */
@Service
public class WorkerServiceImpl implements WorkerService{

    WorkerCrudRepository repository;

    @Autowired
    public WorkerServiceImpl(WorkerCrudRepository repository) {
        this.repository = repository;
    }

    public List<Worker> findAll() {
        return repository.findAll();
    }

    public Worker findById(Long id) {
        return repository.findById(id);
    }

    public Worker insert(Worker worker) {
        return repository.insert(worker);
    }

    public void update(Worker worker) {
        Worker old = repository.findById(worker.getId());
        if (old != null) {
            repository.update(worker);
        }
    }

    public void deleteById(Long id) {
        Worker worker = repository.findById(id);
        if (worker != null) {
            repository.deleteById(id);
        }
    }
}
