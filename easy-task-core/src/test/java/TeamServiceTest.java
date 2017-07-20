import com.easytask.model.enums.Role;
import com.easytask.model.enums.State;
import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.Project;
import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;
import com.easytask.service.ILeaderService;
import com.easytask.service.IProjectService;
import com.easytask.service.ITeamService;
import com.easytask.service.IWorkerService;
import com.easytask.service.impl.TeamServiceImpl;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bojan on 6/24/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class TeamServiceTest {

    @Autowired
    private ITeamService teamService;

    @Autowired
    private IWorkerService workerService;

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IProjectService projectService;

    private static boolean setupFinished = false;
    private static List<Worker> workers;
    private static List<Leader> leaders;

    @Before
    public void createObjects(){
        if(setupFinished)
            return;
        Worker w = new Worker();
        w.setEmail("dummy@mail.com");
        w.setName("Filip");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("bf");
        w = workerService.insert(w);

        Worker w2 = new Worker();
        w2.setEmail("dummyDummy@mail.com");
        w2.setName("Lorem");
        w2.setPassword("loip");
        w2.setRole(Role.ROLE_USER);
        w2.setSurename("Ipsum");
        w2.setUsername("LorI");
        w2 = workerService.insert(w2);

        Worker w3 = new Worker();
        w3.setEmail("mail@mail.com");
        w3.setName("John");
        w3.setPassword("joed");
        w3.setRole(Role.ROLE_USER);
        w3.setSurename("Doe");
        w3.setUsername("JD");
        w3 = workerService.insert(w3);

        Worker w4 = new Worker();
        w4.setEmail("savicaf@mail.com");
        w4.setName("Savica");
        w4.setPassword("pw3");
        w4.setRole(Role.ROLE_USER);
        w4.setSurename("Filipovska");
        w4.setUsername("SF");
        w4 = workerService.insert(w4);

        workers = new ArrayList<Worker>();
        workers.add(w);
        workers.add(w2);
        workers.add(w3);
        workers.add(w4);


        Leader leader = new Leader();
        leader.setWorker(workers.get(0));
        leader = leaderService.insert(leader);

        Leader leader2 = new Leader();
        leader2.setWorker(workers.get(1));
        leader2 = leaderService.insert(leader2);

        Leader leader3 = new Leader();
        leader3.setWorker(workers.get(2));
        leader3 = leaderService.insert(leader3);

        leaders = new ArrayList<Leader>();
        leaders.add(leader);
        leaders.add(leader2);
        leaders.add(leader3);




        setupFinished = true;
    }


    @Test
    public void addDeleteTeam(){

        //creating team #1
        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leaders.get(0));

        Team teamOrecaTest = teamService.insert(teamOreca);
        Assert.assertEquals(teamOreca.getId(), teamService.findById(teamOrecaTest.getId()).getId());

        Assert.assertEquals("Team Oreca", teamOrecaTest.getName());
        teamOreca.setName("Team Oreca 2017");
        //adding workers in the team oreca & checking number of workers in team after addition of workers
        Assert.assertEquals(0, teamOrecaTest.getWorkers().size());
        teamOrecaTest = teamService.insertTeamWorker(teamOrecaTest, workers.get(1));
        Assert.assertEquals("Team Oreca 2017", teamOrecaTest.getName());
        teamOrecaTest = teamService.insertTeamWorker(teamOrecaTest, workers.get(2));
        Assert.assertEquals(2, teamOrecaTest.getWorkers().size());
        teamOrecaTest = teamService.insertTeamWorker(teamOrecaTest, workers.get(3));
        Assert.assertEquals(3, teamOrecaTest.getWorkers().size());

        Assert.assertEquals(1, workerService.findById(workers.get(1).getId()).getTeams().size());
        Assert.assertEquals(1, workerService.findById(workers.get(2).getId()).getTeams().size());
        Assert.assertEquals(1, workerService.findById(workers.get(3).getId()).getTeams().size());

        //creating team #2
        Team teamToyota = new Team();
        teamToyota.setName("Toyota Gazoo Racing");
        teamToyota.setLeader(leaders.get(1));

        Team teamToyotaTest = teamService.insert(teamToyota);
        Assert.assertEquals(teamToyota.getId(), teamService.findById(teamToyotaTest.getId()).getId());

        //adding workers in the team toyota & checking number of workers in team after addition of workers
        Assert.assertEquals(0, teamToyotaTest.getWorkers().size());

        teamToyotaTest = teamService.insertTeamWorker(teamToyotaTest, workers.get(2));
        teamToyotaTest = teamService.insertTeamWorker(teamToyotaTest, workers.get(3));

        Assert.assertEquals(2, teamToyotaTest.getWorkers().size());

        //checking number of team in workers used previously
        Assert.assertEquals(0, workerService.findById(workers.get(0).getId()).getTeams().size());
        Assert.assertEquals(1, workerService.findById(workers.get(1).getId()).getTeams().size());
        Assert.assertEquals(2, workerService.findById(workers.get(2).getId()).getTeams().size());
        Assert.assertEquals(2, workerService.findById(workers.get(3).getId()).getTeams().size());

        //OK until here

        teamToyotaTest = teamService.removeAllTeamWorkers(teamToyotaTest.getId());
        Assert.assertEquals(0, teamService.findById(teamToyotaTest.getId()).getWorkers().size());

        teamToyotaTest = teamService.insertTeamWorker(teamToyotaTest, workers.get(2));
        Assert.assertEquals(1, teamToyotaTest.getWorkers().size());
        Assert.assertEquals(2, workerService.findById(workers.get(2).getId()).getTeams().size());
        teamToyotaTest = teamService.removeTeamWorker(teamToyotaTest, workers.get(2));
        Assert.assertEquals(1, workerService.findById(workers.get(2).getId()).getTeams().size());

        //checking # of wrokers in team Toyota
        Assert.assertEquals(0, teamService.findById(teamToyotaTest.getId()).getWorkers().size());

        teamOrecaTest = teamService.removeTeamWorker(teamOrecaTest, workers.get(1));
        teamOrecaTest = teamService.removeTeamWorker(teamOrecaTest, workers.get(2));
        teamOrecaTest = teamService.removeTeamWorker(teamOrecaTest, workers.get(3));

        //checking # of wrokers in team Oreca
        Assert.assertEquals(0, teamService.findById(teamOrecaTest.getId()).getWorkers().size());

        //checking number of team in workers used previously
        Assert.assertEquals(0, workerService.findById(workers.get(0).getId()).getTeams().size());
        Assert.assertEquals(0, workerService.findById(workers.get(1).getId()).getTeams().size());
        Assert.assertEquals(0, workerService.findById(workers.get(2).getId()).getTeams().size());
        Assert.assertEquals(0, workerService.findById(workers.get(3).getId()).getTeams().size());

        //deliting process is here because this test is last
        //deleting teams
        teamService.deleteById(teamOrecaTest.getId());
        teamService.deleteById(teamToyotaTest.getId());

        Assert.assertEquals(null, teamService.findById(teamOrecaTest.getId()));
        Assert.assertEquals(null, teamService.findById(teamToyotaTest.getId()));

        //deleting @Before objects
        //deleting leaders and workers
        leaderService.deleteById(leaders.get(0).getId());
        leaderService.deleteById(leaders.get(1).getId());
        leaderService.deleteById(leaders.get(2).getId());
        Assert.assertEquals(null, leaderService.findById(leaders.get(0).getId()));
        Assert.assertEquals(null, leaderService.findById(leaders.get(1).getId()));
        Assert.assertEquals(null, leaderService.findById(leaders.get(2).getId()));

        workerService.deleteById(workers.get(0).getId());
        workerService.deleteById(workers.get(1).getId());
        workerService.deleteById(workers.get(2).getId());
        workerService.deleteById(workers.get(3).getId());
        Assert.assertEquals(null, workerService.findById(workers.get(0).getId()));
        Assert.assertEquals(null, workerService.findById(workers.get(1).getId()));
        Assert.assertEquals(null, workerService.findById(workers.get(2).getId()));
        Assert.assertEquals(null, workerService.findById(workers.get(3).getId()));

    }

    @Test
    public void findAllTest(){
        //creating team #1
        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leaders.get(0));

        Team teamOrecaTest = teamService.insert(teamOreca);
        Assert.assertEquals(teamOreca.getId(), teamService.findById(teamOrecaTest.getId()).getId());
        teamOrecaTest = teamService.insertTeamWorker(teamOrecaTest, workers.get(1));
        Assert.assertEquals(1, teamOrecaTest.getWorkers().size());


        //creating team #2
        Team teamToyota = new Team();
        teamToyota.setName("Toyota Gazoo Racing");
        teamToyota.setLeader(leaders.get(1));

        Team teamToyotaTest = teamService.insert(teamToyota);
        Assert.assertEquals(teamToyota.getId(), teamService.findById(teamToyotaTest.getId()).getId());
        teamToyotaTest = teamService.insertTeamWorker(teamToyotaTest, workers.get(2));
        Assert.assertEquals(1, teamToyotaTest.getWorkers().size());


        List<Team> teamsLocal = new ArrayList<Team>();
        teamsLocal.add(teamOrecaTest);
        teamsLocal.add(teamToyotaTest);

        List<Team> teamsDB = teamService.findAll();
        Assert.assertEquals(2, teamsDB.size());

        List<Long> idTeams = new ArrayList<Long>();
        for(Team t : teamsLocal)
            idTeams.add(t.getId());

        for(Team t : teamsDB){
            Assert.assertEquals(true, idTeams.contains(t.getId()));
        }

        teamService.deleteById(teamOrecaTest.getId());
        teamService.deleteById(teamToyotaTest.getId());
        Assert.assertEquals(null, teamService.findById(teamOrecaTest.getId()));
        Assert.assertEquals(null, teamService.findById(teamToyotaTest.getId()));

    }

    @Test
    public void getAllTeamProjects(){
        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leaders.get(0));
        teamOreca = teamService.insert(teamOreca);
        teamOreca = teamService.insertTeamWorker(teamOreca, workers.get(1));
        teamOreca = teamService.insertTeamWorker(teamOreca, workers.get(2));
        teamOreca = teamService.insertTeamWorker(teamOreca, workers.get(3));

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project1");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        project = projectService.insert(project);

        project = new Project();
        project.setName("Project2");
        project.setDescription("Test Project2");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        project = projectService.insert(project);

        Assert.assertEquals(teamService.getAllProjectsByTeam(teamOreca.getId()).size(),2);

        for(Project p : projectService.findAll()){
            projectService.deleteById(p.getId());
        }

        for(Team t : teamService.findAll()){
            teamService.deleteById(t.getId());
        }

        for(Leader l : leaderService.findAll()){
            leaderService.deleteById(l.getId());
        }

        for(Worker w : workerService.findAll()){
            workerService.deleteById(w.getId());
        }
    }

}
