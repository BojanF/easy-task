package com.easytask.service;

import com.easytask.model.enums.State;
import com.easytask.model.jpa.*;

import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */
public interface IWorkerService {

    List<Worker> findAll();

    Worker findById(Long id);

    Worker insert(Worker worker);

    Worker update(Worker worker);

    void deleteById(Long id);

    List<Document> getDocumentsByWorker(Long workerId);

    List<Comment> getCommentsByWorker(Long workerId);

    List<Project> getProjectsByWorker(Long workerId);

    List<Task> getTasksByWorker(Long workerId, State state);

    List<Team> getTeamsLeadByWorker(Long workerId);

    List<Project> getProjectsLeadByWorker(Long workerId);

}
