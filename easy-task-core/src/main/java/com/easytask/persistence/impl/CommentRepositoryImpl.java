package com.easytask.persistence.impl;

import com.easytask.model.jpa.Comment;
import com.easytask.persistence.ICommentRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */
@Primary
@Repository
public class CommentRepositoryImpl implements ICommentRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    @Transactional
    public Comment insert(Comment comment) {
        entityManager.persist(comment);
        entityManager.flush();
        return comment;
    }

    @Transactional
    public Comment findById(Long id) {
        Comment comment = entityManager.find(Comment.class, id);
        if(comment != null){
            entityManager.refresh(comment);
        }
        return comment;
    }

    @Transactional
    public Comment update(Comment comment) {
        comment = entityManager.merge(comment);
        entityManager.flush();
        return comment;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    public List<Comment> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> from = cq.from(Comment.class);
        return entityManager.createQuery(cq).getResultList();
    }
}
