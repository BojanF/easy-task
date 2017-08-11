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

    public List<Task> getTasksForUserByState(Long userId, TaskState state) {
//        List<Task> tasks = new ArrayList<Task>();
//        for (Task t : getTasksForUser(userId)) {
//            if(t.getState() == state)
//                tasks.add(t);
//        }
//        return tasks;
        return userRepository.getTasksForUserByState(userId, state);
    }

    public List<Team> getTeamsLeadByUser(Long userId) {

        List<Team> teams = new ArrayList<Team>();
        for (Team t : getTeamsForUser(userId)) {
            if(t.getLeader().getUser().getId().equals(userId))
                teams.add(t);
        }
        return teams;
    }

    public List<Project> getProjectsLeadByUser(Long userId) {
        return userRepository.getProjectsLeadByUser(userId);
    }

    public List<Team> getTeamsForUser(Long userId) {
        return userRepository.getTeamsForUser(userId);
    }

    public List<Task> getTasksForUser(Long userId){
        return userRepository.getTasksForUser(userId);
    }

    public List<Project> getUrgentProjectsForUser(Long userId){
        return userRepository.getUrgentProjectsForUser(userId);
    }
}
