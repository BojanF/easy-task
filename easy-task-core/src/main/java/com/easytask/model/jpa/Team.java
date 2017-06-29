package com.easytask.model.jpa;

import javax.persistence.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private Long id;

    @NotNull
    private String name;

    @OneToOne
    private Leader leader;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "worker_team",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id")
    )
    private Set<Worker> workers = new HashSet<Worker>();


    //getters
    public Long getId() {
        return id;
    }

    public Leader getLeader() {
        return leader;
    }

    public Set<Worker> getWorkers() {
        return workers;
    }

    public String getName() {
        return name;
    }

    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void setWorkers(Set<Worker> workers) {
        this.workers = workers;
    }

    public void addWorker(Worker worker){
        this.workers.add(worker);
    }

    public void removeWorker(Worker worker){

        Set<Worker> workers =  getWorkers();
        for(Worker w : workers){
            if(w.getId().equals(worker.getId())) {
                this.workers.remove(w);
                break;
            }
        }
    }


}
