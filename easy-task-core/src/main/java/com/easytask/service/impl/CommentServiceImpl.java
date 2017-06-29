package com.easytask.service.impl;

import com.easytask.model.jpa.Comment;
import com.easytask.persistence.ICommentRepository;
import com.easytask.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    ICommentRepository commentRepository;

    public Comment insert(Comment project) {
        return commentRepository.insert(project);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment update(Comment project) {
        return commentRepository.update(project);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }
}
