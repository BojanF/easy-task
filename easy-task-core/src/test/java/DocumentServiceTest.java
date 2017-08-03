import com.easytask.model.enums.ProjectState;
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
 * Created by marijo on 09/07/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class DocumentServiceTest {

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

    private static User user1, user2, user3, user4;
    private static Leader leader1, leader2 ;
    private static Team team1,team2;
    private static Project project;

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


        project = new Project();
        project.setName("Project 1");
        project.setDescription("Test Project");
        project.setCreatedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setState(ProjectState.IN_PROGRESS);
        project.setTeam(team2);
        project = projectService.insert(project);

    }

    @Test
    public void createDocument(){

        Document document = new Document();
        document.setDate(DateTime.now());
        document.setProject(project);
        document.setUrl("kostancev.com/documents/"+document.getId());
        document.setUser(user1);
        document = documentService.insert(document);

        Assert.assertEquals(document.getId(), documentService.findById(document.getId()).getId());
        Assert.assertEquals(document.getProject().getId(),documentService.findById(document.getId()).getProject().getId());
        Assert.assertEquals(document.getUser().getId(),documentService.findById(document.getId()).getUser().getId());

        documentService.deleteById(document.getId());

        Assert.assertEquals(null, documentService.findById(document.getId()));
    }

    @Test
    public void updateDocument(){

        Document document = new Document();
        document.setDate(DateTime.now());
        document.setProject(project);
        document.setUrl("kostancev.com/documents/"+document.getId());
        document.setUser(user1);
        document = documentService.insert(document);

        document.setUrl("kostancev.com/edited_url");
        document = documentService.update(document);

        Assert.assertEquals("kostancev.com/edited_url",documentService.findById(document.getId()).getUrl());

        documentService.deleteById(document.getId());
    }

    @Test
    public void findAllTest(){

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
        document2.setUser(user3);
        document2 = documentService.insert(document2);

        Assert.assertEquals(2, documentService.findAll().size());

        documentService.deleteById(document1.getId());
        Assert.assertEquals(null, documentService.findById(document1.getId()));
        documentService.deleteById(document2.getId());
        Assert.assertEquals(null, documentService.findById(document2.getId()));
    }

    @After
    public void deleteObjects(){
        projectService.deleteById(project.getId());
        teamService.deleteById(team2.getId());
        teamService.deleteById(team1.getId());
        leaderService.deleteById(leader1.getId());
        leaderService.deleteById(leader2.getId());
        userService.deleteById(user1.getId());
        userService.deleteById(user2.getId());
        userService.deleteById(user3.getId());
        userService.deleteById(user4.getId());
    }


}
