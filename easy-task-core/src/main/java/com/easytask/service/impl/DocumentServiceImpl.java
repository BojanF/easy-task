package com.easytask.service.impl;

import com.easytask.model.jpa.Document;
import com.easytask.persistence.IDocumentRepository;
import com.easytask.service.IDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */
@Service
public class DocumentServiceImpl implements IDocumentService {

    @Autowired
    IDocumentRepository repository;

    public List<Document> findAll() {
        return repository.findAll();
    }

    public Document findById(Long id) {
        return repository.findById(id);
    }

    public Document insert(Document document) {
        return repository.insert(document);
    }

    public Document update(Document document) {
        Document old = repository.findById(document.getId());
        if (old != null) {
            document = repository.update(document);
        }
        return document;
    }

    public void deleteById(Long id) {
        Document document = repository.findById(id);
        if (document != null) {
            repository.deleteById(id);
        }
    }
}
