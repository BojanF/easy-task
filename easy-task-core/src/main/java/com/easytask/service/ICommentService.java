package com.easytask.service;

import com.easytask.model.jpa.Comment;

import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */
public interface ICommentService {

    Comment insert(Comment project);

    Comment findById(Long id);

    Comment update(Comment project);

    void deleteById(Long id);

    List<Comment> findAll();

}
