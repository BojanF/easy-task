package com.easytask.scheduled;

import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Task;
import com.easytask.service.IProjectService;
import com.easytask.service.ITaskService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created by Bojan on 8/23/2017.
 */
public class AutomationProcess {

    @Autowired
    ITaskService taskService;

    @Autowired
    IProjectService projectService;

    @Scheduled(cron = "0 0 */1 * * *")
    public void setBreachDeadlineTasks() {
        System.out.println("            Every hour in minute 0 " + DateTime.now());

        List<Task> list = taskService.getDeadlineBreachedTasks(DateTime.now());
        System.out.println("Size: " + list.size());

        for(Task t : list){
            t.setState(TaskState.BREACH_OF_DEADLINE);
            taskService.update(t);
        }
    }

    @Scheduled(cron = "0 15 */1 * * *")
    public void setFinishToUTDProjects() {
        System.out.println("            Every hour in minute 15 " + DateTime.now());

        List<Project> list = projectService.getUpToDateProjectsWithBreachedDeadline(DateTime.now());
        System.out.println("Size: " + list.size());

        for(Project p : list){

            List<Task> projectTasks = projectService.getAllTasksForProject(p.getId());
            DateTime maxCompletedOnDate = projectTasks.get(0).getCompletedOn();
            for(Task t : projectTasks){
                if(t.getCompletedOn().compareTo(maxCompletedOnDate) > 0)
                    maxCompletedOnDate = t.getCompletedOn();
            }
            p.setCompletedOn(maxCompletedOnDate);
            p.setState(ProjectState.FINISHED);
            projectService.update(p);
        }
    }

    @Scheduled(cron = "0 30 */1 * * *")
    public void setBreachDeadlineToProjects() {
        System.out.println("            Every hour in minute 30 " + DateTime.now());

        List<Project> list = projectService.getBreachedProjects(DateTime.now());
        System.out.println("Size: " + list.size());

        for(Project p : list){
            p.setState(ProjectState.BREACH_OF_DEADLINE);
            projectService.update(p);
        }
    }

}