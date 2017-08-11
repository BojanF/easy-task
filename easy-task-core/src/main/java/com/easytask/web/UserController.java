package com.easytask.web;

import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.User;
import com.easytask.service.IUserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */
@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IUserService bean = applicationContext.getBean(IUserService.class);
        System.out.println(bean);
    }

    private IUserService service;

    @Autowired
    public UserController(IUserService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User insertUser(@Valid @RequestBody User user) {
        return service.insert(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        service.update(user);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @RequestMapping(value = "administrating-projects/{id}", method = RequestMethod.GET)
    public List<Project> getAdministratingProjects(@PathVariable Long id) {
        return service.getProjectsLeadByUser(id);
    }
    @RequestMapping(value = "your-projects/{id}", method = RequestMethod.GET)
    public List<Project> getProjectThatUserWorksOn(@PathVariable Long id) {
        return service.getProjectsByUser(id);
    }

}
