import com.easytask.model.enums.Role;
import com.easytask.model.enums.State;
import com.easytask.model.jpa.*;
import com.easytask.service.*;
import com.easytask.service.impl.WorkerServiceImpl;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Bojan on 6/23/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class WorkerServiceTest {

    @Autowired
    private IWorkerService workerService;
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
    public void createDeleteWorker(){

        Worker w = new Worker();
        w.setEmail("dummy@mail.com");
        w.setName("Filip");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("ff");
        w = workerService.insert(w);

        Assert.assertEquals(w.getId(), workerService.findById(w.getId()).getId());

        workerService.deleteById(w.getId());
        Assert.assertEquals(null, workerService.findById(w.getId()));
    }

    @Test
    public  void updateWorker(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Filip");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("bf");
        w = workerService.insert(w);

        Assert.assertEquals(w.getId(), workerService.findById(w.getId()).getId());
        Assert.assertEquals("bf", w.getUsername());

        w.setUsername("BojanF");
        Worker updated = workerService.update(w);

        Assert.assertNotEquals("bf", updated.getUsername());
        Assert.assertEquals("BojanF", updated.getUsername());
        Assert.assertEquals("Filip", updated.getName());

        updated.setEmail("novaMail@mail.com");
        updated.setName("Bojan");
        updated.setSurename("Prezime");
        updated.setPassword("superStrenght");
        updated.setUsername("BojFil");
        updated.setRole(Role.ROLE_ADMIN);
        updated = workerService.update(updated);

        Assert.assertNotEquals("BojanF", updated.getUsername());
        Assert.assertEquals("BojFil", updated.getUsername());
        Assert.assertNotEquals("Filip", updated.getName());
        Assert.assertEquals("Bojan", updated.getName());
        Assert.assertNotEquals("boko@mail.com", updated.getEmail());
        Assert.assertEquals("novaMail@mail.com", updated.getEmail());
        Assert.assertNotEquals("Filipovski", updated.getSurename());
        Assert.assertEquals("Prezime", updated.getSurename());
        Assert.assertNotEquals("pw", updated.getPassword());
        Assert.assertEquals("superStrenght", updated.getPassword());
        Assert.assertNotEquals(Role.ROLE_USER, updated.getRole());
        Assert.assertEquals(Role.ROLE_ADMIN, updated.getRole());

        workerService.deleteById(w.getId());
        Assert.assertEquals(null, workerService.findById(updated.getId()));


    }

    @Test
    public void testfindAll(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Bojan");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("BF");
        w = workerService.insert(w);

        Worker w2 = new Worker();
        w2.setEmail("ivanaf@mail.com");
        w2.setName("Ivana");
        w2.setPassword("pw2");
        w2.setRole(Role.ROLE_USER);
        w2.setSurename("Filipovska");
        w2.setUsername("IF");
        w2 = workerService.insert(w2);

        Worker w3 = new Worker();
        w3.setEmail("savicaf@mail.com");
        w3.setName("Savica");
        w3.setPassword("pw3");
        w3.setRole(Role.ROLE_USER);
        w3.setSurename("Filipovska");
        w3.setUsername("SF");
        w3 = workerService.insert(w3);

        List<Worker> workersLocal = new ArrayList<Worker>();
        workersLocal.add(w);
        workersLocal.add(w2);
        workersLocal.add(w3);

        List<Worker> workersDB = workerService.findAll();
        Assert.assertEquals(3, workersDB.size());

        List<Long> idWorkers = new ArrayList<Long>();
        for(Worker wrk : workersLocal)
            idWorkers.add(wrk.getId());

        for(Worker wrk : workersDB){
            Assert.assertEquals(true, idWorkers.contains(wrk.getId()));
        }

        workerService.deleteById(w3.getId());
        Assert.assertEquals(2, workerService.findAll().size());

        workerService.deleteById(w2.getId());
        workerService.deleteById(w.getId());
        Assert.assertEquals(0, workerService.findAll().size());
    }

    @Test
    public void getCommentsByWorker(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Bojan");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("BF");
        w = workerService.insert(w);

        Leader leader = new Leader();
        leader.setWorker(w);
        leader = leaderService.insert(leader);

        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leader);
        teamOreca = teamService.insert(teamOreca);
        teamOreca = teamService.insertTeamWorker(teamOreca, w);

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        Project insert = projectService.insert(project);


        Comment c = new Comment();
        c.setDate(DateTime.now());
        c.setOnProject(project);
        c.setText("asd");
        c.setCommentOwner(w);
        c = commentService.insert(c);

        c = new Comment();
        c.setDate(DateTime.now());
        c.setOnProject(project);
        c.setText("asd");
        c.setCommentOwner(w);
        c = commentService.insert(c);


        Assert.assertEquals(workerService.getCommentsByWorker(w.getId()).size(),2);

        for (Comment com:commentService.findAll()) {
            commentService.deleteById(com.getId());
        }

        projectService.deleteById(project.getId());
        teamService.deleteById(teamOreca.getId());
        leaderService.deleteById(leader.getId());
        workerService.deleteById(w.getId());

    }

    @Test
    public void getDocumentsByWorker(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Bojan");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("BF");
        w = workerService.insert(w);

        Leader leader = new Leader();
        leader.setWorker(w);
        leader = leaderService.insert(leader);

        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leader);
        teamOreca = teamService.insert(teamOreca);
        teamOreca = teamService.insertTeamWorker(teamOreca, w);

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        Project insert = projectService.insert(project);


        Document d = new Document();
        d.setPostedOn(DateTime.now());
        d.setProject(project);
        d.setUrl("asd");
        d.setWorker(w);
        d = documentService.insert(d);

        d = new Document();
        d.setPostedOn(DateTime.now());
        d.setProject(project);
        d.setUrl("asda");
        d.setWorker(w);
        d = documentService.insert(d);

        d = new Document();
        d.setPostedOn(DateTime.now());
        d.setProject(project);
        d.setUrl("asssd");
        d.setWorker(w);
        d = documentService.insert(d);

        Assert.assertEquals(workerService.getDocumentsByWorker(w.getId()).size(),3);

        for (Document doc:documentService.findAll()) {
            documentService.deleteById(doc.getId());
        }

        projectService.deleteById(project.getId());
        teamService.deleteById(teamOreca.getId());
        leaderService.deleteById(leader.getId());
        workerService.deleteById(w.getId());

    }


    @Test
    public void getTasksByWorker(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Bojan");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("BF");
        w = workerService.insert(w);

        Leader leader = new Leader();
        leader.setWorker(w);
        leader = leaderService.insert(leader);

        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leader);
        teamOreca = teamService.insert(teamOreca);
        teamOreca = teamService.insertTeamWorker(teamOreca, w);

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        Project insert = projectService.insert(project);

        project = new Project();
        project.setName("Project2");
        project.setDescription("Test Project 2");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
       insert = projectService.insert(project);



        Assert.assertEquals(workerService.getProjectsByWorker(w.getId()).size(),2);

        for (Project p : projectService.findAll()){
        projectService.deleteById(p.getId());
        }
        teamService.deleteById(teamOreca.getId());
        leaderService.deleteById(leader.getId());
        workerService.deleteById(w.getId());

    }

    @Test
    public void getProjectsFromWorker(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Bojan");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("BF");
        w = workerService.insert(w);

        Leader leader = new Leader();
        leader.setWorker(w);
        leader = leaderService.insert(leader);

        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leader);
        teamOreca = teamService.insert(teamOreca);
        teamOreca = teamService.insertTeamWorker(teamOreca, w);

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        Project insert = projectService.insert(project);

        Task task = new Task();
        task.setName("task1");
        task.setStartedOn(DateTime.now());
        task.setDeadline(DateTime.now().plusMonths(8));
        task.setTaskState(State.NOT_STARTED);
        task.setLeader(leader);
        task.setProject(project);
        task.addWorker(w);
        task = taskService.insert(task);

        task = new Task();
        task.setName("task2");
        task.setStartedOn(DateTime.now());
        task.setDeadline(DateTime.now().plusMonths(8));
        task.setTaskState(State.IN_PROGRESS);
        task.setLeader(leader);
        task.setProject(project);
        task.addWorker(w);
        task = taskService.insert(task);

        task = new Task();
        task.setName("task3");
        task.setStartedOn(DateTime.now());
        task.setDeadline(DateTime.now().plusMonths(8));
        task.setTaskState(State.IN_PROGRESS);
        task.setLeader(leader);
        task.setProject(project);
        task.addWorker(w);
        task = taskService.insert(task);

        Assert.assertEquals(workerService.getTasksByWorker(w.getId(),State.NOT_STARTED).size(),1);
        Assert.assertEquals(workerService.getTasksByWorker(w.getId(),State.IN_PROGRESS).size(),2);
        Assert.assertEquals(workerService.getTasksByWorker(w.getId(),State.FINISHED).size(),0);

        for (Task t : taskService.findAll()){
            taskService.deleteById(t.getId());
        }
        projectService.deleteById(project.getId());
        teamService.deleteById(teamOreca.getId());
        leaderService.deleteById(leader.getId());
        workerService.deleteById(w.getId());

    }

    @Test
    public void getTeamsLeadByWorker(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Bojan");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("BF");
        w = workerService.insert(w);

        Leader leader = new Leader();
        leader.setWorker(w);
        leader = leaderService.insert(leader);

        Team team = new Team();
        team.setName("Team Oreca");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamWorker(team, w);

        team = new Team();
        team.setName("Team2");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamWorker(team, w);

        team = new Team();
        team.setName("Team3");
        team.setLeader(leader);
        team = teamService.insert(team);
        team = teamService.insertTeamWorker(team, w);



        Assert.assertEquals(workerService.getTeamsLeadByWorker(w.getId()).size(),3);


        for (Team t : teamService.findAll()){
            teamService.deleteById(t.getId());
        }

        leaderService.deleteById(leader.getId());
        workerService.deleteById(w.getId());

    }

    @Test
    public void getProjectsLeadByWorker(){
        Worker w = new Worker();
        w.setEmail("boko@mail.com");
        w.setName("Bojan");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("BF");
        w = workerService.insert(w);

        Worker w2 = new Worker();
        w2.setEmail("ivanaf@mail.com");
        w2.setName("Ivana");
        w2.setPassword("pw2");
        w2.setRole(Role.ROLE_USER);
        w2.setSurename("Filipovska");
        w2.setUsername("IF");
        w2 = workerService.insert(w2);

        Leader leader = new Leader();
        leader.setWorker(w);
        leader = leaderService.insert(leader);

        Team teamOreca = new Team();
        teamOreca.setName("Team Oreca");
        teamOreca.setLeader(leader);
        teamOreca = teamService.insert(teamOreca);
        teamOreca = teamService.insertTeamWorker(teamOreca, w);
        teamOreca = teamService.insertTeamWorker(teamOreca, w2);

        Project project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        Project insert = projectService.insert(project);

        project = new Project();
        project.setName("Project2");
        project.setDescription("Test Project 2");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));
        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamOreca);
        insert = projectService.insert(project);


        Assert.assertEquals(workerService.getProjectsLeadByWorker(w.getId()).size(),2);
        Assert.assertEquals(workerService.getProjectsLeadByWorker(w2.getId()).size(),0);

        for (Project p : projectService.findAll()){
            projectService.deleteById(p.getId());
        }
        teamService.deleteById(teamOreca.getId());
        leaderService.deleteById(leader.getId());
        workerService.deleteById(w.getId());
        workerService.deleteById(w2.getId());

    }

}
