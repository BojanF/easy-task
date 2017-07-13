package com.easytask.persistence.impl;

import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Worker;
import com.easytask.persistence.IDocumentRepository;
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
 * Created by marijo on 05/07/17.
 */
@Primary
@Repository
public class DocumentRepositoryImpl implements IDocumentRepository {

    @PersistenceContext(name = "easy_task_DB")
    EntityManager entityManager;

    @Transactional
    public List<Document> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Document> cq = cb.createQuery(Document.class);
        Root<Document> from = cq.from(Document.class);
        return entityManager.createQuery(cq).getResultList();
    }

    @Transactional
    public Document findById(Long id) {
        Document document = entityManager.find(Document.class, id);
        if (document != null) {
            entityManager.refresh(document);
        }
        return document;
    }

    @Transactional
    public Document insert(Document document) {
        entityManager.persist(document);
        entityManager.flush();
        return document;
    }

    @Transactional
    public Document update(Document document) {
        document = entityManager.merge(document);
        entityManager.flush();
        return document;
    }

    @Transactional
    public void deleteById(Long id) {
        entityManager.remove(findById(id));
    }
}
