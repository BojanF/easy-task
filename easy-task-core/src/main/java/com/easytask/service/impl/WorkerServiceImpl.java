package com.easytask.service.impl;

import com.easytask.model.jpa.Worker;
import com.easytask.persistence.IWorkerRepository;
import com.easytask.service.IWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */
@Service
public class WorkerServiceImpl implements IWorkerService {

    @Autowired
    IWorkerRepository repository;

    public List<Worker> findAll() {
        return repository.findAll();
    }

    public Worker findById(Long id) {
        return repository.findById(id);
    }

    public Worker insert(Worker worker) {
        return repository.insert(worker);
    }

    public Worker update(Worker worker) {
        Worker old = repository.findById(worker.getId());
        if (old != null) {
            worker = repository.update(worker);
        }
        return worker;
    }

    public void deleteById(Long id) {
        Worker worker = repository.findById(id);
        if (worker != null) {
            repository.deleteById(id);
        }
    }
}