package com.easytask.web;

import com.easytask.model.jpa.Task;
import com.easytask.service.ITaskService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bojan on 8/9/2017.
 */
@RestController
@RequestMapping(value = "/api/task", produces = "application/json")
public class TaskController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ITaskService taskServiceBean = applicationContext.getBean(ITaskService.class);
        System.out.println(taskServiceBean);
    }

    private ITaskService taskService;

    @Autowired
    TaskController(ITaskService taskService){
        this.taskService = taskService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Task getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

}
