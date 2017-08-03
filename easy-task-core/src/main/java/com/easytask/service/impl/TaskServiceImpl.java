package com.easytask.service.impl;

import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.User;
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
    ITaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id);
    }

    public Task insert(Task task) {
        return taskRepository.insert(task);
    }

    public Task update(Task task) {
        Task old = taskRepository.findById(task.getId());
        if (old != null) {
            task = taskRepository.update(task);
        }
        return task;
    }

    public void deleteById(Long id) {
        Task task = taskRepository.findById(id);
        if (task != null) {
            taskRepository.deleteById(id);
        }
    }

    public Task addUserToTask(Task task, User user) {
        return taskRepository.addUserOnTask(task, user);
    }

    public Task removeUserFromTask(Task task, User user) {
       return taskRepository.removeUserFromTask(task, user);
    }
}
