package com.easytask.model.jpa;

import javax.persistence.Entity;
import com.easytask.model.enums.TaskState;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Bojan on 6/7/2017.
 */

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(length = 5000)
    public String description;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime createdOn;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime completedOn;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime deadline;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskState state;

    @ManyToOne
    private Leader leader;

    @ManyToOne
    private  Project project;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<User>();

    //getters
    public Long getId() {
        return id;
    }

    public Leader getLeader() {
        return leader;
    }

    public Project getProject() {
        return project;
    }

    public String getName() {
        return name;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public DateTime getCompletedOn() {
        return completedOn;
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public Set<User> getUsers() {
        return users;
    }

    public TaskState getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }


    //setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setLeader(Leader leader) {
        this.leader = leader;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void setCompletedOn(DateTime completedOn) {
        this.completedOn = completedOn;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addUser(User user){
        this.users.add(user);
    }

    public void removeUser(User user){
        Iterator<User> it = users.iterator();
        while(it.hasNext()){
            if (it.next().getId().equals(user.getId())){
                it.remove();
                break;
            }
        }
    }


    //fields
    public static class FIELDS {

        public static String ID = "id";

        public static String NAME = "name";

        public static String CREATED = "createdOn";

        public static String COMPLETED = "completedOn";

        public static String DEADLINE = "deadline";

        public static String STATE = "state";

        public static String USERS = "users";

        public static String DESCRIPTION = "description";

        public static String LEADER = "leader";

        public static String PROJECT = "project";
    }
}
