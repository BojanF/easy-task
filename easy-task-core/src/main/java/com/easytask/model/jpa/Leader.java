package com.easytask.model.jpa;

import javax.persistence.Entity;

import javax.persistence.*;

/**
 * Created by Bojan on 6/7/2017.
 */
@Entity
@Table(name = "leaders")
public class Leader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Worker worker;

    public Integer getId() {
        return id;
    }

    public Worker getWorker() {
        return worker;
    }
}
