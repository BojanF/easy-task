package com.easytask.web;

import com.easytask.model.enums.ProjectState;
import com.easytask.model.jpa.Comment;
import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Task;
import com.easytask.service.IProjectService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Bojan on 8/9/2017.
 */
@RestController
@RequestMapping(value = "/api/project", produces = "application/json")
public class ProjectController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IProjectService projectServiceBean = applicationContext.getBean(IProjectService.class);
        System.out.println(projectServiceBean);
    }

    private IProjectService projectService;

    @Autowired
    ProjectController(IProjectService projectService){
        this.projectService  = projectService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Project insertProject(@Valid @RequestBody Project project) {
        int x = 3;
        project.setState(ProjectState.CREATED);
        x = 4;
        return projectService.insert(project);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public List<Task> getTasksForProject(@PathVariable Long id){
        List<Task> t = projectService.getAllTasksForProject(id);
        int x = 0;

        return t;
//        return projectService.getAllTasksForProject(id);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET)
    public List<Comment> getCommentsForProject(@PathVariable Long id){
        return projectService.getAllCommentsForProject(id);
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
    public List<Document> getDocumentsForProject(@PathVariable Long id){
        return projectService.getAllDocumentsForProject(id);
    }
}
