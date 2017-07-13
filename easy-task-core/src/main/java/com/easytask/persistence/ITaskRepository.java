package com.easytask.persistence;

import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.Worker;

import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */
public interface ITaskRepository {

    List<Task> findAll();

    Task findById(Long id);

    Task insert(Task task);

    Task update(Task task);

    void deleteById(Long id);

    Task insertTaskWorker(Task task, Worker worker);
}
