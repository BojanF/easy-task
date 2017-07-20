package com.easytask.service.impl;

import com.easytask.model.enums.State;
import com.easytask.model.jpa.*;
import com.easytask.persistence.IWorkerRepository;
import com.easytask.service.IWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Document> getDocumentsByWorker(Long workerId) {
        return repository.getDocumentsByWorker(workerId);
    }

    public List<Comment> getCommentsByWorker(Long workerId) {
        return repository.getCommentsByWorker(workerId);
    }

    public List<Project> getProjectsByWorker(Long workerId) {
        return repository.getProjectsByWorker(workerId);
    }

    public List<Task> getTasksByWorker(Long workerId, State state) {
        List<Task> tasks = new ArrayList<Task>();
        for (Task t:
                findById(workerId).getTasks()) {
            if(t.getTaskState()==state)
                tasks.add(t);
        }
        return tasks;
    }

    public List<Team> getTeamsLeadByWorker(Long workerId) {
        List<Team> teams = new ArrayList<Team>();
        for (Team t : findById(workerId).getTeams()) {
            if(t.getLeader().getWorker().getId() == workerId)
                teams.add(t);
        }
        return teams;
    }

    public List<Project> getProjectsLeadByWorker(Long workerId) {
        return repository.getProjectsLeadByWorker(workerId);
    }


}
