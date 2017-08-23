package com.easytask.web;


import com.easytask.model.jpa.CoworkerId;
import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.User;
import com.easytask.service.ICoworkersService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/accept-request", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Coworkers acceptedRequest(@RequestBody Coworkers coworkers){
        int x = 0;
        return coworkersService.acceptRequest(coworkers);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Coworkers> getAll(){
        return coworkersService.findAll();
    }

    @RequestMapping(value = "/get-coworkers/{id}", method = RequestMethod.GET)
    public List<User> getCoworkersForUser(@PathVariable Long id){
        return coworkersService.getCoworkersForUser(id);
    }

    @RequestMapping(value = "/delete/{userA}/{userB}", method = RequestMethod.DELETE)
    public void removeAsCoworker(@PathVariable Long userA, @PathVariable Long userB){
        int x = 0;
        coworkersService.removeAsCoworker(new CoworkerId(userA, userB));
    }

    @RequestMapping(value = "/eligible/{id}", method = RequestMethod.GET)
    public List<User> getEligibleUsers(@PathVariable Long id){
        return coworkersService.getNonEngagedUsersForUser(id);
    }

    @RequestMapping(value = "/received/{id}", method = RequestMethod.GET)
    public List<User> getReceivedRequests(@PathVariable Long id){
        return coworkersService.getCoworkerRequestsReceived(id);
    }

    @RequestMapping(value = "/sent/{id}", method = RequestMethod.GET)
    public List<User> getSentRequests(@PathVariable Long id){
        return coworkersService.getCoworkerRequestsSent(id);
    }

    @RequestMapping(value = "/delete-request/{userA}/{userB}", method = RequestMethod.DELETE)
    public void refuseRequest(@PathVariable Long userA, @PathVariable Long userB){
        coworkersService.deleteById(new CoworkerId(userA, userB));
    }


}
