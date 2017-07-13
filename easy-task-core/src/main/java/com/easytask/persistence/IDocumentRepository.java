package com.easytask.persistence;

import com.easytask.model.jpa.Document;
import com.easytask.model.jpa.Worker;

import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */
public interface IDocumentRepository {

    List<Document> findAll();

    Document findById(Long id);

    Document insert(Document document);

    Document update(Document document);

    void deleteById(Long id);
}
