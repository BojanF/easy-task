package com.easytask.service.impl;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.persistence.IUserRepository;
import com.easytask.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public User insert(User user) {
        return userRepository.insert(user);
    }

    public User update(User user) {
        User old = userRepository.findById(user.getId());
        if (old != null) {
            user = userRepository.update(user);
        }
        return user;
    }

    public void deleteById(Long id) {
        User user = userRepository.findById(id);
        if (user != null) {
            userRepository.deleteById(id);
        }
    }

    public List<Document> getDocumentsByUser(Long userId) {
        return userRepository.getDocumentsByUser(userId);
    }

    public List<Comment> getCommentsByUser(Long userId) {
        return userRepository.getCommentsByUser(userId);
    }

    public List<Project> getProjectsByUser(Long userId) {
        return userRepository.getProjectsByUser(userId);
    }

    public List<Task> getTasksByUser(Long userId, TaskState state) {
        List<Task> tasks = new ArrayList<Task>();
        for (Task t:
                findById(userId).getTasks()) {
                    if(t.getState() == state)
                        tasks.add(t);
        }
        return tasks;
    }

    public List<Team> getTeamsLeadByUser(Long userId) {
        List<Team> teams = new ArrayList<Team>();
        for (Team t : findById(userId).getTeams()) {
            if(t.getLeader().getUser().getId() == userId)
                teams.add(t);
        }
        return teams;
    }

    public List<Project> getProjectsLeadByUser(Long userId) {
        return userRepository.getProjectsLeadByUser(userId);
    }

}
