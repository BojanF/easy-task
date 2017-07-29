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
    IDocumentRepository documentRepository;

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public Document findById(Long id) {
        return documentRepository.findById(id);
    }

    public Document insert(Document document) {
        return documentRepository.insert(document);
    }

    public Document update(Document document) {
        Document old = documentRepository.findById(document.getId());
        if (old != null) {
            document = documentRepository.update(document);
        }
        return document;
    }

    public void deleteById(Long id) {
        Document document = documentRepository.findById(id);
        if (document != null) {
            documentRepository.deleteById(id);
        }
    }
}
