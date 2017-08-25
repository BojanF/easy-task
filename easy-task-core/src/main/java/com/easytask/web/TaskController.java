package com.easytask.web;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.Task;
import com.easytask.service.ITaskService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @RequestMapping(value = "", method = RequestMethod.POST, headers="Accept=application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Task save(@Valid @RequestBody Task task) {
        int x =0;
        task.setState(TaskState.NOT_STARTED);
        return taskService.insert(task);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, headers="Accept=application/json")
    public Task update(@Valid @RequestBody Task task){
        int x = 0;
        return taskService.update(task);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void taskDelete(@PathVariable Long id){
        taskService.deleteById(id);
    }

}
