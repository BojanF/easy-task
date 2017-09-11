package com.easytask.service.impl;

import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Task;
import com.easytask.model.jpa.TasksByProject;
import com.easytask.model.jpa.User;
import com.easytask.persistence.IProjectRepository;
import com.easytask.persistence.ITaskRepository;
import com.easytask.service.ITaskService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by marijo on 05/07/17.
 */
@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    ITaskRepository taskRepository;

    @Autowired
    IProjectRepository projectRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task findById(Long id) {
        return taskRepository.findById(id);
    }

    public Task insert(Task task) {

        Project p = task.getProject();
        ProjectState currentState = p.getState();

        if(currentState == ProjectState.CREATED){
            p.setState(ProjectState.NOT_STARTED); //tested
        }
        else if (currentState==ProjectState.UP_TO_DATE) {
            p.setState(ProjectState.IN_PROGRESS); //tested
        }

        if(currentState != p.getState())
            projectRepository.update(p);

        return taskRepository.insert(task);
    }

    public Task update(Task task) {

        Task old = taskRepository.findById(task.getId());

        if (old != null) {
            if(task.getState() == TaskState.IN_PROGRESS)
                task.setCompletedOn(null);
            task = taskRepository.update(task);

            projectStateManagementAfterUpdate(task);

        }


        return task;
    }

    public void deleteById(Long id) {
        Task task = taskRepository.findById(id);
        if (task != null) {
            taskRepository.deleteById(id);
            Project p = task.getProject();
            ProjectState currentState = p.getState();

            TasksByProject tasksByProject = projectRepository.getTasksStatesByProject(p.getId());
            Long totalTaskNumber = tasksByProject.getTotal();
            int x = 0;
            if(totalTaskNumber.equals(0l)){
                if(DateTime.now().compareTo(p.getDeadline())<=0) //tested
                    p.setState(ProjectState.CREATED);
                else p.setState(ProjectState.BREACH_OF_DEADLINE); //tested
            }
            else if(tasksByProject.getNotStarted().equals(totalTaskNumber)){
                p.setState(ProjectState.NOT_STARTED); //tested
            }
            else if(tasksByProject.getFinished().equals(totalTaskNumber)){
//                p.setState(ProjectState.UP_TO_DATE);
                if(DateTime.now().compareTo(p.getDeadline())<=0)
                    p.setState(ProjectState.UP_TO_DATE); //tested

//                else if(DateTime.now().compareTo(p.getDeadline())>0 && p.getState()!=ProjectState.BREACH_OF_DEADLINE)
//                    p.setState(ProjectState.FINISHED);
                else{
                    p.setState(ProjectState.BREACH_OF_DEADLINE);
                }
            }
            else if(tasksByProject.getBreach().equals(totalTaskNumber)){
                if(DateTime.now().compareTo(p.getDeadline())<=0)
                    p.setState(ProjectState.IN_PROGRESS); //tested
                else p.setState(ProjectState.BREACH_OF_DEADLINE); //tested
            }
            else p.setState(ProjectState.IN_PROGRESS); //tested

            if(currentState != p.getState())
                projectRepository.update(p);
        }
    }

    public Task addUserToTask(Task task, User user) {
        return taskRepository.addUserOnTask(task, user);
    }

    public Task removeUserFromTask(Task task, User user) {
        return taskRepository.removeUserFromTask(task, user);
    }

    public List<Task> getDeadlineBreachedTasks(DateTime now){
        return taskRepository.getDeadlineBreachedTasks(now);
    };

    private void projectStateManagementAfterUpdate(Task task){
        Project p = task.getProject();
        ProjectState currentState = p.getState();

        TasksByProject tasksByProject = projectRepository.getTasksStatesByProject(p.getId());

        if(task.getState()==TaskState.NOT_STARTED ){
            if( tasksByProject.getNotStarted().equals(tasksByProject.getTotal()))
                p.setState(ProjectState.NOT_STARTED); //tested
            else{
                p.setState(ProjectState.IN_PROGRESS);
            }
        }
        else if(task.getState() == TaskState.IN_PROGRESS){
            p.setState(ProjectState.IN_PROGRESS); //tested
        }
        else if(task.getState() == TaskState.FINISHED){
            if(!tasksByProject.getFinished().equals(tasksByProject.getTotal())){
                p.setState(ProjectState.IN_PROGRESS);
            }
            else if(tasksByProject.getFinished().equals(tasksByProject.getTotal()) && DateTime.now().compareTo(p.getDeadline())<=0){
                p.setState(ProjectState.UP_TO_DATE);
            }
            else if(tasksByProject.getFinished().equals(tasksByProject.getTotal()) && DateTime.now().compareTo(p.getDeadline())>0){
                p.setState(ProjectState.FINISHED); //??????????????
                //vajda ne moze da odneses nekoj task vo finished state ako e proektot breach, poso ako proektot e breach
                //site taskovi vo proektot bi bile breach, osven onie koi se finished
            }
        }
        else{
            if(/*tasksByProject.getBreach().equals(tasksByProject.getTotal()) &&*/ DateTime.now().compareTo(p.getDeadline())>0){
                //tested
                p.setState(ProjectState.BREACH_OF_DEADLINE);//tested
            }
            else{
                p.setState(ProjectState.IN_PROGRESS);//tested
            }
        }

        if(currentState != p.getState())
            projectRepository.update(p);
    }
}
