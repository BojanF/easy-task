package com.easytask.service;

import com.easytask.model.jpa.Document;
import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */

public interface IDocumentService {

    Document insert(Document document);

    Document findById(Long id);

    Document update(Document document);

    void deleteById(Long id);

    List<Document> findAll();

}
