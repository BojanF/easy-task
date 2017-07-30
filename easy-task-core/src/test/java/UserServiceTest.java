import com.easytask.model.enums.ProjectState;
import com.easytask.model.enums.TaskState;
import com.easytask.model.jpa.*;
import com.easytask.service.*;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;

/**
 * Created by Bojan on 6/23/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class UserServiceTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private ILeaderService leaderService;
    @Autowired
    private ITeamService teamService;
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private ITaskService taskService;

    @Test
    public void createUser(){

        User user = new User();
        user.setEmail("dummy@mail.com");
        user.setName("Filip");
        user.setPassword("pw");
        user.setSurname("Filipovski");
        user.setUsername("ff");
        user = userService.insert(user);
        
        Assert.assertEquals(user.getId(), userService.findById(user.getId()).getId());

        userService.deleteById(user.getId());
        
        Assert.assertEquals(null, userService.findById(user.getId()));
        
    }

    @Test
    public  void updateUser(){
        User user = new User();
        user.setEmail("boko@mail.com");
        user.setName("Filip");
        user.setPassword("pw");
        user.setSurname("Filipovski");
        user.setUsername("bf");
        user = userService.insert(user);

        Assert.assertEquals(user.getId(), userService.findById(user.getId()).getId());
        Assert.assertEquals("bf", user.getUsername());

        user.setUsername("BojanF");
        
        user= userService.update(user);

        Assert.assertNotEquals("bf", user.getUsername());
        Assert.assertEquals("BojanF", user.getUsername());
        Assert.assertEquals("Filip", user.getName());

        user.setEmail("novaMail@mail.com");
        user.setName("Bojan");
        user.setSurname("Prezime");
        user.setPassword("superStrenght");
        user.setUsername("BojFil");
        
        user = userService.update(user);

        Assert.assertNotEquals("BojanF", user.getUsername());
        Assert.assertEquals("BojFil", user.getUsername());
        Assert.assertNotEquals("Filip", user.getName());
        Assert.assertEquals("Bojan", user.getName());
        Assert.assertNotEquals("boko@mail.com", user.getEmail());
        Assert.assertEquals("novaMail@mail.com", user.getEmail());
        Assert.assertNotEquals("Filipovski", user.getSurname());
        Assert.assertEquals("Prezime", user.getSurname());
        Assert.assertNotEquals("pw", user.getPassword());
        Assert.assertEquals("superStrenght", user.getPassword());

        userService.deleteById(user.getId());
        
        Assert.assertEquals(null, userService.findById(user.getId()));

    }

    @Test
    public void testFindAll(){

        User user1 = new User();
        user1.setEmail("boko@mail.com");
        user1.setName("Bojan");
        user1.setPassword("pw");
        user1.setSurname("Filipovski");
        user1.setUsername("BF");
        user1 = userService.insert(user1);

        User user2 = new User();
        user2.setEmail("ivanaf@mail.com");
        user2.setName("Ivana");
        user2.setPassword("pw2");
        user2.setSurname("Filipovska");
        user2.setUsername("IF");
        user2 = userService.insert(user2);

        User user3 = new User();
        user3.setEmail("savicaf@mail.com");
        user3.setName("Savica");
        user3.setPassword("pw3");
        user3.setSurname("Filipovska");
        user3.setUsername("SF");
        user3 = userService.insert(user3);

        List<User> users = userService.findAll();
        Assert.assertEquals(3, users.size());
        
        userService.deleteById(user3.getId());
        Assert.assertEquals(2, userService.findAll().size());

        userService.deleteById(user2.getId());
        userService.deleteById(user1.getId());
        
        Assert.assertEquals(0, userService.findAll().size());
    }

    @Test
    public void getCommentsByUser(){
        
        User user = new User();
        user.setEmail("boko@mail.com");
        user.setName("Bojan");
        user.setPassword("pw");
        user.setSurname("Filipovski");
        user.setUsername("BF");
        user = userService.insert(user);


        Leader leader = new Leader();
        leader.setUser(user);
        leader = leaderService.insert(leader);


        Team team = new Team();
        team.setName("Team Oreca");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamUser(team, user);


        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.IN_PROGRESS);
        project.setTeam(team);
        project = projectService.insert(project);


        Comment comment1 = new Comment();
        comment1.setDate(DateTime.now());
        comment1.setProject(project);
        comment1.setText("asd");
        comment1.setUser(user);
        comment1 = commentService.insert(comment1);

        Comment comment2 = new Comment();
        comment2.setDate(DateTime.now());
        comment2.setProject(project);
        comment2.setText("asd");
        comment2.setUser(user);
        comment2 = commentService.insert(comment2);


        Assert.assertEquals(userService.getCommentsByUser(user.getId()).size(),2);


        for (Comment comment:commentService.findAll()) {
            commentService.deleteById(comment.getId());
        }
        projectService.deleteById(project.getId());
        teamService.deleteById(team.getId());
        leaderService.deleteById(leader.getId());
        userService.deleteById(user.getId());

    }

    @Test
    public void getDocumentsByUser(){
        
        User user = new User();
        user.setEmail("boko@mail.com");
        user.setName("Bojan");
        user.setPassword("pw");
        user.setSurname("Filipovski");
        user.setUsername("BF");
        user = userService.insert(user);

        
        Leader leader = new Leader();
        leader.setUser(user);
        leader = leaderService.insert(leader);

        
        Team team = new Team();
        team.setName("Team Oreca");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamUser(team, user);

        
        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.IN_PROGRESS);
        project.setTeam(team);
        project = projectService.insert(project);


        Document document1 = new Document();
        document1.setDate(DateTime.now());
        document1.setProject(project);
        document1.setUrl("asd");
        document1.setUser(user);
        document1 = documentService.insert(document1);

        Document document2 = new Document();
        document2.setDate(DateTime.now());
        document2.setProject(project);
        document2.setUrl("asda");
        document2.setUser(user);
        document2 = documentService.insert(document2);

        Document document3 = new Document();
        document3.setDate(DateTime.now());
        document3.setProject(project);
        document3.setUrl("asssd");
        document3.setUser(user);
        document3 = documentService.insert(document3);

        
        Assert.assertEquals(userService.getDocumentsByUser(user.getId()).size(),3);

        
        for (Document doc:documentService.findAll()) {
            documentService.deleteById(doc.getId());
        }

        projectService.deleteById(project.getId());
        teamService.deleteById(team.getId());
        leaderService.deleteById(leader.getId());
        userService.deleteById(user.getId());

    }
    
    @Test
    public void getProjectsByUser(){
        
        User user = new User();
        user.setEmail("boko@mail.com");
        user.setName("Bojan");
        user.setPassword("pw");
        user.setSurname("Filipovski");
        user.setUsername("BF");
        user = userService.insert(user);


        Leader leader = new Leader();
        leader.setUser(user);
        leader = leaderService.insert(leader);


        Team team = new Team();
        team.setName("Team Oreca");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamUser(team, user);


        Project project1 = new Project();
        project1.setName("Project1");
        project1.setDescription("Test Project");
        project1.setCreatedOn(DateTime.now());
        project1.setDeadline(DateTime.now().plusMonths(8));
        project1.setState(ProjectState.IN_PROGRESS);
        project1.setTeam(team);
        project1 = projectService.insert(project1);


        Project project2 = new Project();
        project2.setName("Project2");
        project2.setDescription("Test Project 2");
        project2.setCreatedOn(DateTime.now());
        project2.setDeadline(DateTime.now().plusMonths(8));
        project2.setState(ProjectState.IN_PROGRESS);
        project2.setTeam(team);
        project2 = projectService.insert(project2);


        Assert.assertEquals(userService.getProjectsByUser(user.getId()).size(),2);

        for (Project p : projectService.findAll()){
        projectService.deleteById(p.getId());
        }
        teamService.deleteById(team.getId());
        leaderService.deleteById(leader.getId());
        userService.deleteById(user.getId());
    }

    @Test
    public void getTasksByUser(){

        User user = new User();
        user.setEmail("boko@mail.com");
        user.setName("Bojan");
        user.setPassword("pw");
        user.setSurname("Filipovski");
        user.setUsername("BF");
        user = userService.insert(user);


        Leader leader = new Leader();
        leader.setUser(user);
        leader = leaderService.insert(leader);


        Team team = new Team();
        team.setName("Team Oreca");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamUser(team, user);


        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.IN_PROGRESS);
        project.setTeam(team);
        project = projectService.insert(project);


        Task task1 = new Task();
        task1.setName("task1");
        task1.setCreatedOn(DateTime.now());
        task1.setDeadline(DateTime.now().plusMonths(8));
        task1.setState(TaskState.NOT_STARTED);
        task1.setLeader(leader);
        task1.setProject(project);
        task1.addUser(user);
        task1 = taskService.insert(task1);

        Task task2 = new Task();
        task2.setName("task2");
        task2.setCreatedOn(DateTime.now());
        task2.setDeadline(DateTime.now().plusMonths(8));
        task2.setState(TaskState.IN_PROGRESS);
        task2.setLeader(leader);
        task2.setProject(project);
        task2.addUser(user);
        task2 = taskService.insert(task2);

        Task task3 = new Task();
        task3.setName("task3");
        task3.setCreatedOn(DateTime.now());
        task3.setDeadline(DateTime.now().plusMonths(8));
        task3.setState(TaskState.IN_PROGRESS);
        task3.setLeader(leader);
        task3.setProject(project);
        task3.addUser(user);
        task3 = taskService.insert(task3);


        Assert.assertEquals(userService.getTasksByUser(user.getId(),TaskState.NOT_STARTED).size(),1);
        Assert.assertEquals(userService.getTasksByUser(user.getId(),TaskState.IN_PROGRESS).size(),2);
        Assert.assertEquals(userService.getTasksByUser(user.getId(),TaskState.FINISHED).size(),0);

        for (Task t : taskService.findAll()){
            taskService.deleteById(t.getId());
        }
        projectService.deleteById(project.getId());
        teamService.deleteById(team.getId());
        leaderService.deleteById(leader.getId());
        userService.deleteById(user.getId());

    }

    @Test
    public void getTeamsLeadByUser(){

        User user = new User();
        user.setEmail("boko@mail.com");
        user.setName("Bojan");
        user.setPassword("pw");
        user.setSurname("Filipovski");
        user.setUsername("BF");
        user = userService.insert(user);


        Leader leader = new Leader();
        leader.setUser(user);
        leader = leaderService.insert(leader);


        Team team1 = new Team();
        team1.setName("Team1");
        team1.setLeader(leader);
        team1 = teamService.insert(team1);
        team1 = teamService.insertTeamUser(team1, user);


        Team team2 = new Team();
        team2.setName("Team2");
        team2.setLeader(leader);
        team2 = teamService.insert(team2);
        team2 = teamService.insertTeamUser(team2, user);

        Team team3 = new Team();
        team3.setName("Team3");
        team3.setLeader(leader);
        team3 = teamService.insert(team3);
        team3 = teamService.insertTeamUser(team3, user);


        Assert.assertEquals(userService.getTeamsLeadByUser(user.getId()).size(),3);

        for (Team t : teamService.findAll()){
            teamService.deleteById(t.getId());
        }
        leaderService.deleteById(leader.getId());
        userService.deleteById(user.getId());

    }

    @Test
    public void getProjectsLeadByUser(){

        User user1 = new User();
        user1.setEmail("boko@mail.com");
        user1.setName("Bojan");
        user1.setPassword("pw");
        user1.setSurname("Filipovski");
        user1.setUsername("BF");
        user1 = userService.insert(user1);

        User user2 = new User();
        user2.setEmail("ivanaf@mail.com");
        user2.setName("Ivana");
        user2.setPassword("pw2");
        user2.setSurname("Filipovska");
        user2.setUsername("IF");
        user2 = userService.insert(user2);


        Leader leader = new Leader();
        leader.setUser(user1);
        leader = leaderService.insert(leader);


        Team team = new Team();
        team.setName("Team Oreca");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamUser(team, user1);
        team = teamService.insertTeamUser(team, user2);


        Project project1 = new Project();
        project1.setName("Project1");
        project1.setDescription("Test Project");
        project1.setCreatedOn(DateTime.now());
        project1.setDeadline(DateTime.now().plusMonths(8));
        project1.setState(ProjectState.IN_PROGRESS);
        project1.setTeam(team);
        project1 = projectService.insert(project1);

        Project project2 = new Project();
        project2.setName("Project2");
        project2.setDescription("Test Project 2");
        project2.setCreatedOn(DateTime.now());
        project2.setDeadline(DateTime.now().plusMonths(8));
        project2.setState(ProjectState.IN_PROGRESS);
        project2.setTeam(team);
        project2 = projectService.insert(project2);


        Assert.assertEquals(userService.getProjectsLeadByUser(user1.getId()).size(),2);
        Assert.assertEquals(userService.getProjectsLeadByUser(user2.getId()).size(),0);

        for (Project p : projectService.findAll()){
            projectService.deleteById(p.getId());
        }
        teamService.deleteById(team.getId());
        leaderService.deleteById(leader.getId());
        userService.deleteById(user1.getId());
        userService.deleteById(user2.getId());

    }

}
