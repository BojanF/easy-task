package com.easytask.web;

import com.easytask.model.jpa.Team;
import com.easytask.service.ITeamService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Bojan on 8/9/2017.
 */

@RestController
@RequestMapping(value = "/api/team", produces = "application/json")
public class TeamController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ITeamService teamServiceBean = applicationContext.getBean(ITeamService.class);
        System.out.println(teamServiceBean);
    }

    private ITeamService teamService;

    @Autowired
    TeamController(ITeamService teamService){
        this.teamService = teamService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Team insertTeam(@RequestBody Team team){
        return teamService.insert(team);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Team getById(@PathVariable Long id) {
        return teamService.findById(id);
    }
}
