package com.easytask.web;

import com.easytask.model.jpa.Worker;
import com.easytask.service.IWorkerService;
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
@RequestMapping(value = "/api/workers", produces = "application/json")
public class WorkerController implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IWorkerService bean = applicationContext.getBean(IWorkerService.class);
        System.out.println(bean);
    }

    private IWorkerService service;

    @Autowired
    public WorkerController(IWorkerService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Worker insertWorker(@Valid @RequestBody Worker worker) {
        return service.insert(worker);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateWorker(@PathVariable Long id, @RequestBody Worker worker) {
        service.update(worker);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Worker> getAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Worker getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

}
