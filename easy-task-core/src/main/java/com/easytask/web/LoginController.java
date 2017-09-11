package com.easytask.web;

import com.easytask.model.jpa.User;
import com.easytask.model.pojos.UserPojo;
import com.easytask.service.IUserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Marijo on 02-Sep-17.
 */

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class LoginController {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IUserService bean = applicationContext.getBean(IUserService.class);
        System.out.println(bean);
    }

    private IUserService service;


    @Autowired
    public LoginController(IUserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@Valid @RequestBody UserPojo user) {
        System.out.println("LOGIN "+user.getPassword());
        User u =  service.getUserByUsername(user.getUsername());
        System.out.println(BCrypt.checkpw(user.getPassword(),u.getPassword()));
        if (BCrypt.checkpw(user.getPassword(),u.getPassword()))
        {
            return u;}
        else
            return null;

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@RequestBody User user) {
        System.out.println("REGISTER "+user.getPassword());
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        return service.insert(user);
    }

}
