
import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.service.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by marijo on 09/07/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class TaskServiceTest {

    @Autowired
    IProjectService projectService;

    @Autowired
    IUserService userService;

    @Autowired
    ILeaderService leaderService;

    @Autowired
    ITeamService teamService;

    @Autowired
    ITaskService taskService;

    private static boolean setupFinished = false;
    private static User user1 , user2, user3, user4;
    private static Leader leader1, leader2 ;
    private static Team team1,team2;
    private static Project project;

    @Before
    public void createObjects(){
        
        if(setupFinished)
            return;

        user1 = new User();
        user1.setEmail("dummy@mail.com");
        user1.setName("Filip");
        user1.setPassword("pw");
        user1.setSurename("Filipovski");
        user1.setUsername("bf");
        user1 = userService.insert(user1);

        user2 = new User();
        user2.setEmail("dummyDummy@mail.com");
        user2.setName("Lorem");
        user2.setPassword("loip");
        user2.setSurename("Ipsum");
        user2.setUsername("LorI");
        user2 = userService.insert(user2);

        user3 = new User();
        user3.setEmail("mail@mail.com");
        user3.setName("John");
        user3.setPassword("joed");
        user3.setSurename("Doe");
        user3.setUsername("JD");
        user3 = userService.insert(user3);

        user4 = new User();
        user4.setEmail("savicaf@mail.com");
        user4.setName("Savica");
        user4.setPassword("pw3");
        user4.setSurename("Filipovska");
        user4.setUsername("SF");
        user4 = userService.insert(user4);

        
        leader1 = new Leader();
        leader1.setUser(user1);
        leader1 = leaderService.insert(leader1);

        leader2 = new Leader();
        leader2.setUser(user2);
        leader2 = leaderService.insert(leader2);

        team1 = new Team();
        team1.setName("Team 1");
        team1.setLeader(leader1);
        team1.addUser(user2);
        team1.addUser(user3);
        team1.addUser(user4);
        team1 = teamService.insert(team1);

        team2 = new Team();
        team2.setName("Team 2");
        team2.setLeader(leader2);
        team2.addUser(user3);
        team2.addUser(user4);
        team2 = teamService.insert(team2);
        
        
        project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.IN_PROGRESS);
        project.setTeam(team2);
        project = projectService.insert(project);


        setupFinished = true;
    }

    @Test
    public void createTask() {

        Task task = new Task();
        task.setName("task");
        task.setCreatedOn(DateTime.now());
        task.setDeadline(DateTime.now().plusMonths(8));
        task.setState(TaskState.NOT_STARTED);
        task.setLeader(leader1);
        task.addUser(user2);
        task.setProject(project);
        task = taskService.insert(task);

        Assert.assertEquals(task.getId(), taskService.findById(task.getId()).getId());
        Assert.assertEquals(task.getProject().getId(), taskService.findById(task.getId()).getProject().getId());
        Assert.assertEquals(task.getLeader().getId(), taskService.findById(task.getId()).getLeader().getId());

        taskService.deleteById(task.getId());

        Assert.assertEquals(null, taskService.findById(task.getId()));

    }

    @Test
    public void updateTask(){

        Task task = new Task();
        task.setName("task");
        task.setCreatedOn(DateTime.now());
        task.setDeadline(DateTime.now().plusMonths(8));
        task.setState(TaskState.NOT_STARTED);
        task.setLeader(leader1);
        task.addUser(user2);
        task.setProject(project);
        task = taskService.insert(task);

        Assert.assertEquals(task.getId(), taskService.findById(task.getId()).getId());

        task.setDeadline(DateTime.now().plusMonths(9));
        task.setName("task new");
        task.setState(TaskState.IN_PROGRESS);
        task = taskService.update(task);

        Assert.assertEquals(task.getDeadline().getMonthOfYear(),taskService.findById(task.getId()).getDeadline().getMonthOfYear());
        Assert.assertEquals(task.getName(),taskService.findById(task.getId()).getName());
        Assert.assertEquals(task.getState(), taskService.findById(task.getId()).getState());

        taskService.deleteById(task.getId());
    }

    @Test
    public void findAllTest(){

        Task task1 = new Task();
        task1.setName("task1");
        task1.setCreatedOn(DateTime.now());
        task1.setDeadline(DateTime.now().plusMonths(8));
        task1.setState(TaskState.NOT_STARTED);
        task1.setLeader(leader1);
        task1.addUser(user2);
        task1.setProject(project);
        task1 = taskService.insert(task1);

        Task task2 = new Task();
        task2.setName("task2");
        task2.setCreatedOn(DateTime.now());
        task2.setDeadline(DateTime.now().plusMonths(3));
        task2.setState(TaskState.NOT_STARTED);
        task2.setLeader(leader1);
        task2.setProject(project);
        task2 = taskService.insert(task2);

        Assert.assertEquals(2, taskService.findAll().size());

        taskService.deleteById(task1.getId());
        Assert.assertEquals(null, taskService.findById(task1.getId()));
        taskService.deleteById(task2.getId());
        Assert.assertEquals(null, taskService.findById(task2.getId()));
    }

    @Test
    public void taskUserCRUD() {

        Task task = new Task();
        task.setName("task");
        task.setCreatedOn(DateTime.now());
        task.setDeadline(DateTime.now().plusMonths(8));
        task.setState(TaskState.NOT_STARTED);
        task.setLeader(leader1);
        task.addUser(user2);
        task.setProject(project);
        task = taskService.insert(task);

        Assert.assertEquals(task.getId(), taskService.findById(task.getId()).getId());

        task = taskService.addUserToTask(task,user3);
        task = taskService.addUserToTask(task,user4);

        Assert.assertEquals(3, task.getUsers().size());

        task = taskService.removeUserFromTask(task,user3);

        Assert.assertEquals(2, task.getUsers().size());

        taskService.deleteById(task.getId());

    }



}
