package com.easytask.persistence.impl;

import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.model.pojos.DocumentResponse;
import com.easytask.persistence.IProjectRepository;
import org.joda.time.DateTime;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */
@Primary
@Repository
public class ProjectRepositoryImpl implements IProjectRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    @Transactional
    public Project insert(Project project) {
        entityManager.persist(project);
        entityManager.flush();
        return project;
    }

    @Transactional
    public Project findById(Long id) {
        Project project = entityManager.find(Project.class, id);
        if(project != null){
            entityManager.refresh(project);
        }
        return project;
    }

    @Transactional
    public Project update(Project project) {
        project = entityManager.merge(project);
        entityManager.flush();
        return project;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));

    }

    @Transactional
    public List<Project> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from = cq.from(Project.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<Task> getAllTasksForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> from= cq.from(Task.class);
        cq.where(
                cb.equal(
                        from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<Document> getAllDocumentsForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> cq = cb.createQuery(Document.class);
        Root<Document> from= cq.from(Document.class);
        cq.where(
                cb.equal(
                        from.get(Document.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<DocumentResponse> getAllDocumentsForProjectWithoutData(Long projectId){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentResponse> cq = cb.createQuery(DocumentResponse.class);
        Root<Document> root = cq.from(Document.class);
        cq.multiselect(root.get(Document.FIELDS.ID), root.get(Document.FIELDS.NAME),root.get(Document.FIELDS.DATE),root.get(Document.FIELDS.USER),root.get(Document.FIELDS.SIZE), root.get(Document.FIELDS.ICON))
        .where(
                cb.equal(
                        root.get(Document.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public List<Comment> getAllCommentsForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
        Root<Comment> from= cq.from(Comment.class);
        cq.where(
                cb.equal(
                        from.get(Comment.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        ).orderBy(cb.desc(from.get(Comment.FIELDS.DATE)));
        return entityManager.createQuery(cq).getResultList();
    }

//    @Transactional
//    public List<TaskState> getAllTaskStatesForTasksOnProject(Long projectId) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<TaskState> cq = cb.createQuery(TaskState.class);
//        Root<Task> from= cq.from(Task.class);
//        cq.select(from.get(Task.FIELDS.STATE).as(TaskState.class)).where(
//                cb.equal(
//                        from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.ID),
//                        projectId
//                )
//        );
//        return entityManager.createQuery(cq).getResultList();
//    }

    public TasksByProject getTasksStatesByProject(Long projectId){

        TasksByProject result = entityManager.createQuery("SELECT tbp FROM TasksByProject tbp WHERE tbp.id=:id", TasksByProject.class).setParameter("id", projectId).getSingleResult();
        return result;
    };

//    public List<Task> getTasksForUserOnProject(Long projectId, Long userId){
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
//        Root<Task> from= cq.from(Task.class);
//        cq.where(
//                cb.equal(
//                        from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.ID),
//                        projectId
//                ),
//                cb.equal(from.get(Task.FIELDS.))
//        );
//        return entityManager.createQuery(cq).getResultList();
//    }

    @Transactional
    public int deleteAllTasksForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Task> cq = cb.createCriteriaDelete(Task.class);
        Root<Task> from= cq.from(Task.class);
        cq.where(
                cb.equal(
                        from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).executeUpdate();
    }

    @Transactional
    public int deleteAllCommentsForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Comment> cq = cb.createCriteriaDelete(Comment.class);
        Root<Comment> from= cq.from(Comment.class);
        cq.where(
                cb.equal(
                        from.get(Comment.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).executeUpdate();
    }

    @Transactional
    public int deleteAllDocumentsForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete<Document> cq = cb.createCriteriaDelete(Document.class);
        Root<Document> from= cq.from(Document.class);
        cq.where(
                cb.equal(
                        from.get(Document.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).executeUpdate();
    }

    //counters

    public Integer getNumberOfCommentsForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Comment> from= cq.from(Comment.class);
        cq.select(from.get(Comment.FIELDS.ID).as(Long.class)).where(
                cb.equal(
                        from.get(Comment.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).getResultList().size();
    }

    public Integer getNumberOfDocumentsForProject(Long projectId){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Document> root = cq.from(Document.class);
        cq.select(root.get(Document.FIELDS.ID).as(Long.class))
                .where(
                        cb.equal(
                                root.get(Document.FIELDS.PROJECT).get(Project.FIELDS.ID),
                                projectId
                        )
                );
        return entityManager.createQuery(cq).getResultList().size();
    }

    public Integer getNumberOfTasksForProject(Long projectId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Task> from= cq.from(Task.class);
        cq.select(from.get(Task.FIELDS.ID).as(Long.class)).where(
                cb.equal(
                        from.get(Task.FIELDS.PROJECT).get(Project.FIELDS.ID),
                        projectId
                )
        );
        return entityManager.createQuery(cq).getResultList().size();
    }

    public List<Project> getBreachedProjects(DateTime now){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from = cq.from(Project.class);

        cq.select(from).where(
                cb.or(
                    cb.equal(from.get(Project.FIELDS.STATE), ProjectState.CREATED),
                    cb.equal(from.get(Project.FIELDS.STATE), ProjectState.NOT_STARTED),
                    cb.equal(from.get(Project.FIELDS.STATE), ProjectState.IN_PROGRESS)
                ),
                cb.lessThan(from.get(Project.FIELDS.DEADLINE).as(DateTime.class), now)
        );

        return entityManager.createQuery(cq).getResultList();
    }

    public List<Project> getUpToDateProjectsWithBreachedDeadline(DateTime now){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> from = cq.from(Project.class);

        cq.select(from).where(

                cb.equal(from.get(Project.FIELDS.STATE), ProjectState.UP_TO_DATE),
                cb.lessThan(from.get(Project.FIELDS.DEADLINE).as(DateTime.class), now)

        );

        return entityManager.createQuery(cq).getResultList();
    }


}
