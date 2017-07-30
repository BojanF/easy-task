
import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.service.*;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Bojan on 6/28/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class ProjectServiceTest {

    @Autowired
    IProjectService projectService;

    @Autowired
    IUserService userService;

    @Autowired
    ILeaderService leaderService;

    @Autowired
    ITeamService teamService;

    @Autowired
    IDocumentService documentService;

    @Autowired
    ICommentService commentService;

    @Autowired
    ITaskService taskService;

    private static User user1 , user2, user3, user4;
    private static Leader leader1, leader2 ;
    private static Team team1,team2;

    @Before
    public void createObjects(){

        user1 = new User();
        user1.setEmail("dummy@mail.com");
        user1.setName("Filip");
        user1.setPassword("pw");
        user1.setSurname("Filipovski");
        user1.setUsername("bf");
        user1 = userService.insert(user1);

        user2 = new User();
        user2.setEmail("dummyDummy@mail.com");
        user2.setName("Lorem");
        user2.setPassword("loip");
        user2.setSurname("Ipsum");
        user2.setUsername("LorI");
        user2 = userService.insert(user2);

        user3 = new User();
        user3.setEmail("mail@mail.com");
        user3.setName("John");
        user3.setPassword("joed");
        user3.setSurname("Doe");
        user3.setUsername("JD");
        user3 = userService.insert(user3);

        user4 = new User();
        user4.setEmail("savicaf@mail.com");
        user4.setName("Savica");
        user4.setPassword("pw3");
        user4.setSurname("Filipovska");
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

    }

    @Test
    public void createProject() {

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("test");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.NOT_STARTED);
        project.setTeam(team2);
        project = projectService.insert(project);

        Assert.assertEquals(project.getId(), projectService.findById(project.getId()).getId());
        Assert.assertEquals(team2.getId(), projectService.findById(project.getId()).getTeam().getId());
        Assert.assertEquals("Project1", projectService.findById(project.getId()).getName());

        projectService.deleteById(project.getId());
        Assert.assertEquals(null, projectService.findById(project.getId()));
    }

    public void delete(){

        //deleting @Before objects
        teamService.removeAllTeamUsers(team1.getId());
        teamService.removeAllTeamUsers(team2.getId());
        teamService.deleteById(team1.getId());
        teamService.deleteById(team2.getId());
        leaderService.deleteById(leader1.getId());
        leaderService.deleteById(leader2.getId());
        userService.deleteById(user1.getId());
        userService.deleteById(user2.getId());
        userService.deleteById(user3.getId());
        userService.deleteById(user4.getId());

        Assert.assertEquals(0, userService.findAll().size());

    }

    @Test
    public void updateProject(){
        
        Project project = new Project();
        project.setName("Project1");
        project.setDescription("desc");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.NOT_STARTED);
        project.setTeam(team1);
        project = projectService.insert(project);

        Assert.assertEquals(project.getId(), projectService.findById(project.getId()).getId());
        
        Assert.assertEquals(null, project.getCompletedOn());
        project.setCompletedOn(DateTime.now().plusMonths(8).minusDays(2));
        project = projectService.update(project);
        Assert.assertNotEquals(null, project.getCompletedOn());

        project.setCreatedOn(DateTime.now().plusDays(1));
        project.setDeadline(DateTime.now().plusMonths(9));
        project.setCompletedOn(DateTime.now().plusMonths(9).minusDays(1));
        project.setState(ProjectState.FINISHED);
        project.setDescription("changed desc");
        project.setName("Project 1 new");
        project = projectService.update(project);

        Assert.assertEquals(project.getName(), projectService.findById(project.getId()).getName());
        Assert.assertEquals(project.getDescription(), projectService.findById(project.getId()).getDescription());
        Assert.assertEquals(project.getState(), projectService.findById(project.getId()).getState());
        
        projectService.deleteById(project.getId());
        
        Assert.assertEquals(null, projectService.findById(project.getId()));

    }

    @Test
    public void findAllTest(){

        Project project1 = new Project();
        project1.setName("Project1");
        project1.setDescription("desc");
        project1.setCreatedOn(DateTime.now());
        project1.setDeadline(DateTime.now().plusMonths(8));
        project1.setState(ProjectState.NOT_STARTED);
        project1.setTeam(team2);
        project1 = projectService.insert(project1);

        Project project2 = new Project();
        project2.setName("Project2");
        project2.setDescription("desc");
        project2.setCreatedOn(DateTime.now().plusMonths(1));
        project2.setDeadline(DateTime.now().plusMonths(8));
        project2.setState(ProjectState.NOT_STARTED);
        project2.setTeam(team1);
        project2 = projectService.insert(project2);
        
        Assert.assertEquals(2, projectService.findAll().size());
        
        projectService.deleteById(project1.getId());
        Assert.assertEquals(null, projectService.findById(project1.getId()));
        projectService.deleteById(project2.getId());
        Assert.assertEquals(null, projectService.findById(project2.getId()));
    }

    @Test
    public void nonCrudTest(){
        
        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.IN_PROGRESS);
        project.setTeam(team2);
        project = projectService.insert(project);


        Document document1 = new Document();
        document1.setDate(DateTime.now());
        document1.setProject(project);
        document1.setUrl("kostancev.com/documents/"+document1.getId());
        document1.setUser(user1);
        document1 = documentService.insert(document1);

        Document document2 = new Document();
        document2.setDate(DateTime.now());
        document2.setProject(project);
        document2.setUrl("kostancev.com/documents/"+document2.getId());
        document2.setUser(user1);
        document2 = documentService.insert(document2);


        Comment comment1 = new Comment();
        comment1.setUser(user1);
        comment1.setDate(DateTime.now());
        comment1.setProject(project);
        comment1.setText("text");
        comment1 = commentService.insert(comment1);

        Comment comment2 = new Comment();
        comment2.setUser(user1);
        comment2.setDate(DateTime.now());
        comment2.setProject(project);
        comment2.setText("text");
        comment2 = commentService.insert(comment2);

        Comment comment3 = new Comment();
        comment3.setUser(user1);
        comment3.setDate(DateTime.now());
        comment3.setProject(project);
        comment3.setText("text");
        comment3 = commentService.insert(comment3);


        Task task1 = new Task();
        task1.setName("task1");
        task1.setCreatedOn(DateTime.now());
        task1.setDeadline(DateTime.now().plusMonths(8));
        task1.setState(TaskState.NOT_STARTED);
        task1.setLeader(leader1);
        task1.setProject(project);
        task1.addUser(user1);
        task1.addUser(user2);
        task1 = taskService.insert(task1);

        Task task2 = new Task();
        task2.setName("task2");
        task2.setCreatedOn(DateTime.now());
        task2.setDeadline(DateTime.now().plusMonths(8));
        task2.setState(TaskState.NOT_STARTED);
        task2.setLeader(leader1);
        task2.setProject(project);
        task2.addUser(user1);
        task2.addUser(user2);
        task2 = taskService.insert(task2);

        Task task3 = new Task();
        task3.setName("task3");
        task3.setCreatedOn(DateTime.now());
        task3.setDeadline(DateTime.now().plusMonths(8));
        task3.setState(TaskState.NOT_STARTED);
        task3.setLeader(leader1);
        task3.setProject(project);
        task3.addUser(user1);
        task3.addUser(user3);
        task3 = taskService.insert(task3);


        Assert.assertEquals(projectService.getAllDocumentsForProject(project.getId()).size(),2);
        Assert.assertEquals(projectService.getAllCommentsForProject(project.getId()).size(),3);
        Assert.assertEquals(projectService.getAllTasksForProject(project.getId()).size(),3);
        Assert.assertEquals(projectService.getAllTasksForUserOnProject(project.getId(),user1.getId()).size(),3);
        Assert.assertEquals(projectService.getAllTasksForUserOnProject(project.getId(),user2.getId()).size(),2);
        Assert.assertEquals(projectService.getAllTasksForUserOnProject(project.getId(),user3.getId()).size(),1);


        for(Task t : taskService.findAll()){
            taskService.deleteById(t.getId());
        }

        for(Comment c : commentService.findAll()){
            commentService.deleteById(c.getId());
        }

        for(Document d : documentService.findAll()){
            documentService.deleteById(d.getId());
        }

        for(Project p : projectService.findAll()){
            projectService.deleteById(p.getId());
        }


    }

    @After
    public void deleteObjects(){
        teamService.deleteById(team1.getId());
        teamService.deleteById(team2.getId());
        leaderService.deleteById(leader1.getId());
        leaderService.deleteById(leader2.getId());
        userService.deleteById(user1.getId());
        userService.deleteById(user2.getId());
        userService.deleteById(user3.getId());
        userService.deleteById(user4.getId());
    }

}
