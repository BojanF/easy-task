import com.easytask.model.enums.ProjectState;
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
public class CommentServiceTest {

    @Autowired
    IProjectService projectService;

    @Autowired
    IUserService userService;

    @Autowired
    ILeaderService leaderService;

    @Autowired
    ITeamService teamService;

    @Autowired
    ICommentService commentService;

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
        user1.setSurename("Filiposki");
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
        project.setName("Project 1");
        project.setDescription("Test Project");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.IN_PROGRESS);
        project.setTeam(team2);
        project = projectService.insert(project);


        setupFinished = true;
    }
    
    @Test
    public void createComment(){
        
        Comment comment = new Comment();
        comment.setUser(user1);
        comment.setDate(DateTime.now());
        comment.setProject(project);
        comment.setText("text");
        comment = commentService.insert(comment);

        Assert.assertEquals(comment.getId(), commentService.findById(comment.getId()).getId());
        Assert.assertEquals(comment.getProject().getId(),commentService.findById(comment.getId()).getProject().getId());
        Assert.assertEquals(comment.getUser().getId(),commentService.findById(comment.getId()).getUser().getId());
        
        commentService.deleteById(comment.getId());
        
        Assert.assertEquals(null,commentService.findById(comment.getId()));
    }

    @Test
    public void updateComment(){
        
        Comment comment = new Comment();
        comment.setUser(user1);
        comment.setDate(DateTime.now());
        comment.setProject(project);
        comment.setText("text");
        comment = commentService.insert(comment);
        
        comment.setText("Edited text");
        comment = commentService.update(comment);

        Assert.assertEquals(commentService.findById(comment.getId()).getText(),"Edited text");

        commentService.deleteById(comment.getId());
    }

    @Test
    public void findAllTest(){

        Comment comment1 = new Comment();
        comment1.setUser(user1);
        comment1.setDate(DateTime.now());
        comment1.setProject(project);
        comment1.setText("text");
        comment1 = commentService.insert(comment1);

        Comment comment2 = new Comment();
        comment2.setUser(user2);
        comment2.setDate(DateTime.now());
        comment2.setProject(project);
        comment2.setText("text");
        comment2 = commentService.insert(comment2);

        Assert.assertEquals(2, commentService.findAll().size());

        commentService.deleteById(comment1.getId());
        Assert.assertEquals(null, commentService.findById(comment1.getId()));

        commentService.deleteById(comment2.getId());
        Assert.assertEquals(null, commentService.findById(comment2.getId()));
    }

}
