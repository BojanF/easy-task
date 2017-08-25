package com.easytask.scheduled;

import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.Task;
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

//    @Scheduled(fixedRate = 5000)
//    public void everyFiveSeconds(){
//        System.out.println("Every five seconds: " + DateTime.now());
//    }

    @Scheduled(cron = "0 29 16 ? * *")
    public void every5Seconds() {
        System.out.println("TOCNO VO 12:20 " + DateTime.now());

//        List<Task> list = taskService.getDeadlineBreachedTasks(DateTime.now());
//        System.out.println("Size: " + list.size());
//
//        for(Task t : list){
//            t.setState(TaskState.BREACH_OF_DEADLINE);
//            taskService.update(t);
//        }
    }
//
//    @Scheduled(cron = "0 22 12 ? * *")
//    public void onceInADay() {
//        System.out.println("TOCNO VO 12:22" + DateTime.now());
//        List<Task> list = taskService.getDeadlineBreachedTasks(DateTime.now());
//        System.out.println("Size: " + list.size());
//    }

}