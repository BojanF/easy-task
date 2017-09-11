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

    @Scheduled(cron = "0 1 */1 * * *")
    public void setBreachDeadlineTasks() {
        System.out.println("            Every hour in minute 1 " + DateTime.now());

        List<Task> list = taskService.getDeadlineBreachedTasks(DateTime.now());
        System.out.println("Size: " + list.size());

        for(Task t : list){
            t.setState(TaskState.BREACH_OF_DEADLINE);
            taskService.update(t);
        }
    }

    @Scheduled(cron = "0 16 */1 * * *")
    public void setFinishToUTDProjects() {
        System.out.println("            Every hour in minute 16 " + DateTime.now());

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

    @Scheduled(cron = "0 31 */1 * * *")
    public void setBreachDeadlineToProjects() {
        System.out.println("            Every hour in minute 31 " + DateTime.now());

        DateTime now = DateTime.now();
        List<Project> list = projectService.getBreachedProjects(now);
        System.out.println("Size: " + list.size());

        for(Project p : list){
            List<Task> breachedTasks = projectService.getDeadlineBreachedTasksForProject(p.getId(), now);
            for(Task t : breachedTasks){
                t.setState(TaskState.BREACH_OF_DEADLINE);
                taskService.update(t);
            }
            if(breachedTasks.size() == 0) {
                System.out.println("            VLEZE UPDATE");
                p.setState(ProjectState.BREACH_OF_DEADLINE);
                projectService.update(p);
            }
            else{
                System.out.println("            NE UPDATE");
            }
        }
    }

}