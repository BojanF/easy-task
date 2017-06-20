package com.easytask.model.jpa;

import javax.persistence.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Bojan on 6/7/2017.
 */
@Entity
@Table(name = "teams")
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Leader leader;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "worker_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private Set<Worker> workers = new HashSet<Worker>();

    public Integer getId() {
        return id;
    }

    public Leader getLeader() {
        return leader;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }
}
