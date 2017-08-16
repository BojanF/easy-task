package com.easytask.web;


import com.easytask.model.jpa.User;
import com.easytask.service.ICoworkersService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Bojan on 8/15/2017.
 */
@RestController
@RequestMapping(value = "/api/coworkers", produces = "application/json")
public class CoworkersController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ICoworkersService bean = applicationContext.getBean(ICoworkersService.class);
        System.out.println(bean);
    }

    private ICoworkersService coworkersService;

    @Autowired
    public CoworkersController(ICoworkersService coworkersService) {
        this.coworkersService = coworkersService;
    }

    @RequestMapping(value = "/get-coworkers/{id}", method = RequestMethod.GET)
    public List<User> getCoworkersForUser(@PathVariable Long id){
        return coworkersService.getCoworkersForUser(id);
    }
}
