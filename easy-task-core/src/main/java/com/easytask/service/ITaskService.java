package com.easytask.service;

import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;

import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */
public interface ITaskService {

    Task insert(Task task);

    Task findById(Long id);

    Task update(Task task);

    void deleteById(Long id);

    List<Task> findAll();

    Task insertTaskWorker(Task task, Worker worker);
}
