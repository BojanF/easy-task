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
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bojan on 6/28/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class ProjectServiceTest {

    @Autowired
    IProjectService projectService;

    @Autowired
    IWorkerService workerService;

    @Autowired
    ILeaderService leaderService;

    @Autowired
    ITeamService teamService;

    private static boolean setupFinished = false;
    private static List<Worker> workers = new ArrayList<Worker>();
    private static List<Leader> leaders = new ArrayList<Leader>();
    private static List<Team> teams = new ArrayList<Team>();

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

        leaders = new ArrayList<Leader>();
        leaders.add(leader);
        leaders.add(leader2);

        //team one
        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leaders.get(0));
        teamOreca = teamService.insert(teamOreca);
        teamOreca = teamService.insertTeamWorker(teamOreca, workers.get(1));
        teamOreca = teamService.insertTeamWorker(teamOreca, workers.get(2));
        teamOreca = teamService.insertTeamWorker(teamOreca, workers.get(3));

        //team two
        Team teamToyota = new Team();
        teamToyota.setName("Toyota Gazoo Racing");
        teamToyota.setLeader(leaders.get(1));
        teamToyota = teamService.insert(teamToyota);
        teamToyota = teamService.insertTeamWorker(teamToyota, workers.get(2));
        teamToyota = teamService.insertTeamWorker(teamToyota, workers.get(3));

        teams.add(teamOreca);
        teams.add(teamToyota);



        setupFinished = true;
    }

    @Test
    public void createDeleteProject(){
        
        Project projectToyota = new Project();
        projectToyota.setName("LMP1 Le Mans 2018");
        projectToyota.setDescription("Project for Toyota T060 for Endurance Season 2018");
        projectToyota.setStartedOn(DateTime.now());
        projectToyota.setDeadline(DateTime.now().plusMonths(8));
        projectToyota.setProjectState(State.NOT_STARTED);
        projectToyota.setProjectTeam(teams.get(1));

        Project projectToyotaTest = projectService.insert(projectToyota);

        //Project projectFromDB = projectService.findById(projectToyotaTest.getId());
        Assert.assertEquals(projectToyota.getId(), projectToyotaTest.getId());
        Assert.assertEquals(teams.get(1).getId(), projectToyotaTest.getProjectTeam().getId());
        Assert.assertEquals(teams.get(1).getName(),  projectToyotaTest.getProjectTeam().getName());


        //deleting project
        projectService.deleteById(projectToyotaTest.getId());
        Assert.assertEquals(null, projectService.findById(projectToyotaTest.getId()));


        //deleting @Before objects
        teamService.removeAllTeamWorkers(teams.get(0).getId());
        teamService.removeAllTeamWorkers(teams.get(1).getId());
        teamService.deleteById(teams.get(0).getId());
        teamService.deleteById(teams.get(1).getId());
        leaderService.deleteById(leaders.get(0).getId());
        leaderService.deleteById(leaders.get(1).getId());
        workerService.deleteById(workers.get(0).getId());
        workerService.deleteById(workers.get(1).getId());
        workerService.deleteById(workers.get(2).getId());
        workerService.deleteById(workers.get(3).getId());

        Assert.assertEquals(0, workerService.findAll().size());

    }

    @Test
    public void updateTest(){
        Project projectToyota = new Project();
        projectToyota.setName("LMP1 Le Mans 2018");
        projectToyota.setDescription("Project for Toyota T060 for Endurance Season 2018");
        projectToyota.setStartedOn(DateTime.now());
        projectToyota.setDeadline(DateTime.now().plusMonths(8));
        projectToyota.setProjectState(State.NOT_STARTED);
        projectToyota.setProjectTeam(teams.get(1));

        Project projectToyotaTest = projectService.insert(projectToyota);

        Assert.assertEquals(projectToyota.getId(), projectToyotaTest.getId());
        Assert.assertEquals(teams.get(1).getId(), projectToyotaTest.getProjectTeam().getId());
        Assert.assertEquals(teams.get(1).getName(),  projectToyotaTest.getProjectTeam().getName());

        //update
        Assert.assertEquals(null, projectToyotaTest.getFinishedOn());
        projectToyotaTest.setFinishedOn(DateTime.now().plusMonths(8).minusDays(2));
        projectToyotaTest = projectService.update(projectToyotaTest);
        Assert.assertNotEquals(null, projectToyotaTest.getFinishedOn());


        //test update function
        Project beforeUpdate = projectService.findById(projectToyotaTest.getId());

        projectToyotaTest.setStartedOn(DateTime.now().plusDays(1));
        projectToyotaTest.setDeadline(DateTime.now().plusMonths(9));
        projectToyotaTest.setFinishedOn(DateTime.now().plusMonths(9).minusDays(1));
        projectToyotaTest.setProjectState(State.FINISHED);
        projectToyotaTest.setDescription("WE`RE GONNA WIN LE MANS IN 2018!!!");
        projectToyotaTest.setName("LMP1 Le Mans 2018 TS060");
        projectToyotaTest = projectService.update(projectToyotaTest);

        Assert.assertNotEquals(projectToyotaTest.getName(), beforeUpdate.getName());
        Assert.assertNotEquals(projectToyotaTest.getDescription(), beforeUpdate.getDescription());
        Assert.assertNotEquals(projectToyotaTest.getStartedOn(), beforeUpdate.getStartedOn());
        Assert.assertNotEquals(projectToyotaTest.getFinishedOn(), beforeUpdate.getFinishedOn());
        Assert.assertNotEquals(projectToyotaTest.getDeadline(), beforeUpdate.getDeadline());
        Assert.assertNotEquals(projectToyotaTest.getProjectState(), beforeUpdate.getProjectState());

        //deleting project
        projectService.deleteById(projectToyotaTest.getId());
        Assert.assertEquals(null, projectService.findById(projectToyotaTest.getId()));

    }

    @Test
    public void findAllTest(){

        //creating projects
        Project projectToyota = new Project();
        projectToyota.setName("LMP1 Le Mans 2018");
        projectToyota.setDescription("Project for Toyota T060 for Endurance Season 2018");
        projectToyota.setStartedOn(DateTime.now());
        projectToyota.setDeadline(DateTime.now().plusMonths(8));
        projectToyota.setProjectState(State.NOT_STARTED);
        projectToyota.setProjectTeam(teams.get(1));
        Project projectToyotaTest = projectService.insert(projectToyota);

        Project projectOreca = new Project();
        projectOreca.setName("LMP2 2018 Project");
        projectOreca.setDescription("Project for 2018 ednurance season");
        projectOreca.setStartedOn(DateTime.now().plusMonths(1));
        projectOreca.setDeadline(DateTime.now().plusMonths(8));
        projectOreca.setProjectState(State.NOT_STARTED);
        projectOreca.setProjectTeam(teams.get(0));
        Project projectOrecaTest = projectService.insert(projectOreca);

        Assert.assertEquals(projectToyota.getId(), projectToyotaTest.getId());
        Assert.assertEquals(projectOrecaTest.getId(), projectOrecaTest.getId());



        List<Project> projectsLocal = new ArrayList<Project>();
        projectsLocal.add(projectToyotaTest);
        projectsLocal.add(projectOrecaTest);

        List<Project> projectsDB = projectService.findAll();
        Assert.assertEquals(2, projectsDB.size());

        List<Long> idProjects = new ArrayList<Long>();
        for(Project p : projectsLocal)
            idProjects.add(p.getId());

        for(Project p : projectsDB)
            Assert.assertEquals(true, idProjects.contains(p.getId()));

        //deleting project
        projectService.deleteById(projectToyotaTest.getId());
        Assert.assertEquals(null, projectService.findById(projectToyotaTest.getId()));
        projectService.deleteById(projectOrecaTest.getId());
        Assert.assertEquals(null, projectService.findById(projectOrecaTest.getId()));
    }

}
