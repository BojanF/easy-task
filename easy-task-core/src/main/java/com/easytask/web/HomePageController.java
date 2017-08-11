package com.easytask.web;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.User;
import com.easytask.service.IUserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bojan on 8/8/2017.
 */
@RestController
@RequestMapping(value = "/api/home-page", produces = "application/json")
public class HomePageController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IUserService userServiceBean = applicationContext.getBean(IUserService.class);
        System.out.println(userServiceBean);
    }

    private IUserService userService;

    @Autowired
    HomePageController(IUserService userService){
        this.userService = userService;
    }

    @RequestMapping(value = "/tasks-states/{id}", method = RequestMethod.GET)
    public List<Integer> geTasksStates(@PathVariable Long id) {
        List<Integer> tasksStates = new ArrayList<Integer>();

        tasksStates.add(userService.getTasksForUserByState(id, TaskState.NOT_STARTED).size());
        tasksStates.add(userService.getTasksForUserByState(id, TaskState.IN_PROGRESS).size());
        tasksStates.add(userService.getTasksForUserByState(id, TaskState.FINISHED).size());
        tasksStates.add(userService.getTasksForUserByState(id, TaskState.BREACH_OF_DEADLINE).size());

        return tasksStates;
    }

    @RequestMapping(value = "/active-tasks/{id}", method = RequestMethod.GET)
    public List<Task> getActiveTasks(@PathVariable Long id) {
        List<Task> activeTasks = new ArrayList<Task>();

        activeTasks.addAll(userService.getTasksForUserByState(id, TaskState.NOT_STARTED));
        activeTasks.addAll(userService.getTasksForUserByState(id, TaskState.IN_PROGRESS));

        return activeTasks;
    }

    @RequestMapping(value = "/urgent-projects/{id}", method = RequestMethod.GET)
    public List<Project> getUrgentProjects(@PathVariable Long id) {
        int x = 0;
        return userService.getUrgentProjectsForUser(id);
    }

    @RequestMapping(value = "/tasks-by-state/{id}/{state}", method = RequestMethod.GET)
    public List<Task> getTasksByState(@PathVariable Long id, @PathVariable int state){
        TaskState stateObj = null;
        List<Task> tasks = new ArrayList<Task>();
        int x =0;
        if(state == 1)
            stateObj = TaskState.NOT_STARTED;
        else if(state == 2)
            stateObj = TaskState.IN_PROGRESS;
        else if(state == 3)
            stateObj = TaskState.FINISHED;
        else if(state == 4)
            stateObj = TaskState.BREACH_OF_DEADLINE;
        else
            return tasks;

        tasks = userService.getTasksForUserByState(id, stateObj);
        return tasks;
    }
}
