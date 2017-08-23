package com.easytask.model.pojos;

import com.easytask.model.jpa.User;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Created by Marijo on 23-Aug-17.
 */
public class DocumentResponse {

    private Long id;

    private String name;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime date;

    private User user;

    public DocumentResponse(Long id, String name, DateTime date, User user) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DateTime getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
