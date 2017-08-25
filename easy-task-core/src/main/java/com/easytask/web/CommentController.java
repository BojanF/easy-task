package com.easytask.web;

import com.easytask.model.jpa.Comment;
import com.easytask.service.ICommentService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Bojan on 8/13/2017.
 */
@RestController
@RequestMapping(value = "/api/comment", produces = "application/json")
public class CommentController {

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ICommentService taskServiceBean = applicationContext.getBean(ICommentService.class);
        System.out.println(taskServiceBean);
    }

    private ICommentService commentService;

    @Autowired
    CommentController(ICommentService commentService){
        this.commentService = commentService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Comment save(@Valid @RequestBody Comment comment) {
        int x =0;
        return commentService.insert(comment);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteComment(@PathVariable Long id){
        commentService.deleteById(id);
    }
}
