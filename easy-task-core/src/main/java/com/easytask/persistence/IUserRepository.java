package com.easytask.persistence;

import com.easytask.model.jpa.*;
import java.util.List;

/**
 * Created by Marijo on 21-Jun-17.
 */

public interface IUserRepository {

    List<User> findAll();

    User findById(Long id);

    User insert(User user);

    User update(User user);

    void deleteById(Long id);

    List<Document> getDocumentsByUser(Long userId);

    List<Comment> getCommentsByUser(Long userId);

    List<Project> getProjectsByUser(Long userId);

    List<Project> getProjectsLeadByUser(Long userId);

    //TODO Da go vrakja passwordot za username i mail
}
