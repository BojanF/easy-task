package com.easytask.persistence;

import com.easytask.model.jpa.Comment;
import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */

public interface ICommentRepository {

    Comment insert(Comment comment);

    Comment findById(Long id);

    Comment update(Comment comment);

    void deleteById(Long id);

    List<Comment> findAll();
}
