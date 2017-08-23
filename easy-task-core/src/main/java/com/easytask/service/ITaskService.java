package com.easytask.service;

import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.User;
import org.joda.time.DateTime;

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

    Task addUserToTask(Task task, User user);

    Task removeUserFromTask(Task task, User user);

    List<Task> getDeadlineBreachedTasks(DateTime now);
}
