package com.easytask.service.impl;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.persistence.IProjectRepository;
import com.easytask.persistence.ITeamRepository;
import com.easytask.persistence.IUserRepository;
import com.easytask.service.IProjectService;
import com.easytask.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        List<Task> tasks = new ArrayList<Task>();
//        for (Task t : getTasksForUser(userId)) {
//            if(t.getState() == state)
//                tasks.add(t);
//        }
//        return tasks;
        return userRepository.getTasksForUserByState(userId, state);
    }

    public List<Team> getTeamsLeadByUser(Long userId) {
//        komentirano od Bojan 14.8
//        List<Team> teams = new ArrayList<Team>();
//        for (Team t : getTeamsForUser(userId)) {
//            if(t.getLeader().getUser().getId().equals(userId))
//                teams.add(t);
//        }
//        return teams;
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

    //donut all tasks
    public List<Integer> getTaskStatesForAllProjectsLedByUser(Long userId){
        List<Integer> resultList = new ArrayList<Integer>();
        List<TaskState> allStates = new ArrayList<TaskState>();

        List<Project> projectsLedByUser = getProjectsLeadByUser(userId);
        for(Project p : projectsLedByUser)
            allStates.addAll(projectRepository.getAllTaskStatesForTasksOnProject(p.getId()));

        int notStarted = 0;
        int inProgress = 0;
        int finished = 0;
        int breach = 0;
        for(TaskState ts : allStates){
            if(ts == TaskState.NOT_STARTED)
                notStarted++;
            else if(ts == TaskState.IN_PROGRESS)
                inProgress++;
            else if(ts == TaskState.FINISHED)
                finished++;
            else breach++;
        }

        resultList.add(notStarted);
        resultList.add(inProgress);
        resultList.add(finished);
        resultList.add(breach);
        return resultList;
    }

    public List<Long> projectStatsLeader(Long userId){
        return userRepository.projectStatsLeader(userId);
    };

    public List<Long> tasksStatsLeader(Long userId){
        return userRepository.tasksStatsLeader(userId);
    };
}
