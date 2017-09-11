package com.easytask.service.impl;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.persistence.IProjectRepository;
import com.easytask.persistence.ITeamRepository;
import com.easytask.persistence.IUserRepository;
import com.easytask.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITeamRepository teamRepository;

    @Autowired
    IProjectRepository projectRepository;

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
        return userRepository.getTasksForUserByState(userId, state);
    }

    public List<Team> getTeamsLeadByUser(Long userId) {
        return userRepository.getTeamsLeadByUser(userId);
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

    public List<Long> projectStatsLeader(Long userId){
        return userRepository.projectStatsLeader(userId);
    };

    public List<Long> tasksStatsLeader(Long userId){
        return userRepository.tasksStatsLeader(userId);
    };

    public List<TeamLeader> getTeamsInfoLeadByUser(Long leaderId){
        return userRepository.getTeamsInfoLeadByUser(leaderId);
    };

    public List<TeamLeader> getTeamsInfoTeamsForUser(Long userId){
        return userRepository.getTeamsInfoTeamsForUser(userId);
    };

    public List<Task> getUrgentTask(Long userId){
        return userRepository.getUrgentTask(userId);
    };

    public List<Team> getTeamsMemberOf(Long userId){
        return userRepository.getTeamsMemberOf(userId);
    };

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }





}
