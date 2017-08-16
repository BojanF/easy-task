package com.easytask.web;

import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.User;
import com.easytask.service.ILeaderService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Bojan on 8/15/2017.
 */
@RestController
@RequestMapping(value = "/api/leader", produces = "application/json")
public class LeaderController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ILeaderService bean = applicationContext.getBean(ILeaderService.class);
        System.out.println(bean);
    }

    private ILeaderService leaderService;

    @Autowired
    public LeaderController(ILeaderService leaderService) {
        this.leaderService = leaderService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Leader insertLeader(@Valid @RequestBody Leader leader) {
        return leaderService.insert(leader);
    }

    @RequestMapping(value = "/get-leader/{id}", method = RequestMethod.GET)
    public Leader getLeaderForUserId(@PathVariable Long id){
        Leader l = leaderService.getLeaderForUserId(id);
        int x = 0;
        return l;
    }



}
