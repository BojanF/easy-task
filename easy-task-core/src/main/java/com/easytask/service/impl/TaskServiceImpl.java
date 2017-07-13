package com.easytask.service.impl;

import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.Worker;
import com.easytask.persistence.ITaskRepository;
import com.easytask.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */
@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    ITaskRepository repository;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task findById(Long id) {
        return repository.findById(id);
    }

    public Task insert(Task task) {
        return repository.insert(task);
    }

    public Task update(Task task) {
        Task old = repository.findById(task.getId());
        if (old != null) {
            task = repository.update(task);
        }
        return task;
    }

    public void deleteById(Long id) {
        Task task = repository.findById(id);
        if (task != null) {
            repository.deleteById(id);
        }
    }

    public Task insertTaskWorker(Task task, Worker worker) {
        return repository.insertTaskWorker(task, worker);
    }
}
