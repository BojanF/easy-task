
import com.easytask.model.enums.ProjectState;
import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.User;
import com.easytask.service.ILeaderService;
import com.easytask.service.IProjectService;
import com.easytask.service.ITeamService;
import com.easytask.service.IUserService;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Bojan on 6/24/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class TeamServiceTest {

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IProjectService projectService;

    private static boolean setupFinished = false;
    private static User user1,user2,user3,user4;
    private static Leader leader1,leader2,leader3;

    @Before
    public void createObjects(){
        
        if(setupFinished)
            return;
        
        User user1 = new User();
        user1.setEmail("dummy@mail.com");
        user1.setName("Filip");
        user1.setPassword("pw");
        user1.setSurename("Filipovski");
        user1.setUsername("bf");
        user1 = userService.insert(user1);

        User user2 = new User();
        user2.setEmail("dummyDummy@mail.com");
        user2.setName("Lorem");
        user2.setPassword("loip");
        user2.setSurename("Ipsum");
        user2.setUsername("LorI");
        user2 = userService.insert(user2);

        User user3 = new User();
        user3.setEmail("mail@mail.com");
        user3.setName("John");
        user3.setPassword("joed");
        user3.setSurename("Doe");
        user3.setUsername("JD");
        user3 = userService.insert(user3);

        User user4 = new User();
        user4.setEmail("savicaf@mail.com");
        user4.setName("Savica");
        user4.setPassword("pw3");
        user4.setSurename("Filipovska");
        user4.setUsername("SF");
        user4 = userService.insert(user4);
        

        Leader leader1 = new Leader();
        leader1.setUser(user1);
        leader1 = leaderService.insert(leader1);

        Leader leader2 = new Leader();
        leader2.setUser(user2);
        leader2 = leaderService.insert(leader2);

        Leader leader3 = new Leader();
        leader3.setUser(user3);
        leader3 = leaderService.insert(leader3);
        

        setupFinished = true;
    }


    @Test
    public void createTeam() {

        Team team = new Team();
        team.setName("Team 1");
        team.setLeader(leader1);
        team.addUser(user2);
        team.addUser(user3);
        team.addUser(user4);
        team = teamService.insert(team);

        Assert.assertEquals(team.getId(), teamService.findById(team.getId()).getId());
        Assert.assertEquals("Team 1", team.getName());
        Assert.assertEquals(3, team.getUsers().size());

        Assert.assertEquals(1, userService.findById(user2.getId()).getTeams().size());

        teamService.deleteById(team.getId());

        Assert.assertEquals(null, teamService.findById(team.getId()));

    }

    @Test
    public void updateTeam() {

        Team team = new Team();
        team.setName("Team 1");
        team.setLeader(leader1);
        team.addUser(user2);
        team = teamService.insert(team);

        Assert.assertEquals(team.getId(), teamService.findById(team.getId()).getId());

        team.setName("Team 1 new");
        team.addUser(user3);
        team = teamService.update(team);

        Assert.assertEquals("Team 1 new", team.getName());
        Assert.assertEquals(2, team.getUsers().size());

        teamService.deleteById(team.getId());
    }

    @Test
    public void teamUserCRUD() {

        Team team = new Team();
        team.setName("Team 1");
        team.setLeader(leader1);
        team.addUser(user2);
        team = teamService.insert(team);

        Assert.assertEquals(team.getId(), teamService.findById(team.getId()).getId());

        team = teamService.insertTeamUser(team,user3);
        team = teamService.insertTeamUser(team,user4);

        Assert.assertEquals(3, team.getUsers().size());

        team = teamService.removeTeamUser(team,user3);

        Assert.assertEquals(2, team.getUsers().size());

        team = teamService.removeAllTeamUsers(team.getId());

        Assert.assertEquals(0, team.getUsers().size());

        teamService.deleteById(team.getId());
    }
    
    //TODO check this
    public void deleteObjects(){
        //deleting @Before objects
        //deleting leaders and users
        leaderService.deleteById(leader1.getId());
        leaderService.deleteById(leader2.getId());
        leaderService.deleteById(leader3.getId());
     

        userService.deleteById(user1.getId());
        userService.deleteById(user2.getId());
        userService.deleteById(user3.getId());
        userService.deleteById(user4.getId());
   
    }

    @Test
    public void findAllTest(){

        Team team1 = new Team();
        team1.setName("Team 1");
        team1.setLeader(leader1);
        team1.addUser(user2);
        team1 = teamService.insert(team1);
        
        Assert.assertEquals(team1.getId(), teamService.findById(team1.getId()).getId());

        Team team2 = new Team();
        team2.setName("Team 2");
        team2.setLeader(leader1);
        team2.addUser(user3);
        team2 = teamService.insert(team2);

        Assert.assertEquals(team2.getId(), teamService.findById(team2.getId()).getId());

        Assert.assertEquals(2,  teamService.findAll().size());

        teamService.deleteById(team1.getId());
        teamService.deleteById(team2.getId());

    }

    @Test
    public void getAllTeamProjects(){

        Team team1 = new Team();
        team1.setName("Team 1");
        team1.setLeader(leader1);
        team1.addUser(user2);
        team1.addUser(user3);
        team1.addUser(user4);
        team1 = teamService.insert(team1);

        Project project1 = new Project();
        project1.setName("Project1");
        project1.setDescription("Test Project1");
        project1.setCreatedOn(DateTime.now());
        project1.setDeadline(DateTime.now().plusMonths(8));
        project1.setState(ProjectState.IN_PROGRESS);
        project1.setTeam(team1);
        project1 = projectService.insert(project1);

        Project project2 = new Project();
        project2.setName("Project2");
        project2.setDescription("Test Project2");
        project2.setCreatedOn(DateTime.now());
        project2.setDeadline(DateTime.now().plusMonths(8));
        project2.setState(ProjectState.IN_PROGRESS);
        project2.setTeam(team1);
        project2 = projectService.insert(project2);

        Assert.assertEquals(2, teamService.getAllProjectsByTeam(team1.getId()).size());

        for(Project p : projectService.findAll()){
            projectService.deleteById(p.getId());
        }

        for(Team t : teamService.findAll()){
            teamService.deleteById(t.getId());
        }

        for(Leader l : leaderService.findAll()){
            leaderService.deleteById(l.getId());
        }

        for(User u : userService.findAll()){
            userService.deleteById(u.getId());
        }
    }

}
