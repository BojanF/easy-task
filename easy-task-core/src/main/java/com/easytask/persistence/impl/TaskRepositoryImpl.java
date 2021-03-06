package com.easytask.persistence.impl;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.User;
import com.easytask.persistence.ITaskRepository;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */

@Primary
@Repository
public class TaskRepositoryImpl implements ITaskRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    @Transactional
    public List<Task> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> from = cq.from(Task.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public Task findById(Long id) {
        Task task = entityManager.find(Task.class, id);
        if (task != null) {
            entityManager.refresh(task);
        }
        return task;
    }

    @Transactional
    public Task insert(Task task) {
        entityManager.persist(task);
        entityManager.flush();
        return task;
    }

    @Transactional
    public Task update(Task task) {
        task = entityManager.merge(task);
        entityManager.flush();
        return task;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }

    @Transactional
    public Task addUserOnTask(Task task, User user) {
        task.addUser(user);
        return update(task);
    }

    @Transactional
    public Task removeUserFromTask(Task task, User user) {
        task.removeUser(user);
        return update(task);
    }

    public List<Task> getDeadlineBreachedTasks(DateTime now){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> from = cq.from(Task.class);

        cq.select(from).where(
                cb.or(
                        cb.equal(from.get(Task.FIELDS.STATE),
                                TaskState.NOT_STARTED),
                        cb.equal(from.get(Task.FIELDS.STATE),
                                TaskState.IN_PROGRESS)
                ),
                cb.lessThan(from.get(Task.FIELDS.DEADLINE).as(DateTime.class), now)
        );

        return entityManager.createQuery(cq).getResultList();
    }
}
