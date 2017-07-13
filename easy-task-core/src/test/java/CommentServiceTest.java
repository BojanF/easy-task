import com.easytask.model.enums.Role;
import com.easytask.model.enums.State;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marijo on 09/07/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class CommentServiceTest {

    @Autowired
    IProjectService projectService;

    @Autowired
    IWorkerService workerService;

    @Autowired
    ILeaderService leaderService;

    @Autowired
    ITeamService teamService;

    @Autowired
    ICommentService commentService;

    private static boolean setupFinished = false;
    private static List<Worker> workers = new ArrayList<Worker>();
    private static List<Leader> leaders = new ArrayList<Leader>();
    private static List<Team> teams = new ArrayList<Team>();
    private static Project project;
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

        project = new Project();
        project.setName("Project1");
        project.setDescription("Test Project");
        project.setStartedOn(DateTime.now());
        project.setDeadline(DateTime.now().plusMonths(8));

        project.setProjectState(State.IN_PROGRESS);
        project.setProjectTeam(teamToyota);
        Project insert = projectService.insert(project);


        setupFinished = true;
    }

    @Test
    public void createComment(){
        Comment comment = new Comment();
        comment.setCommentOwner(workers.get(0));
        comment.setDate(DateTime.now());
        comment.setOnProject(project);
        comment.setText("text");
        Comment insert = commentService.insert(comment);

        Assert.assertEquals(comment.getId(), commentService.findById(comment.getId()).getId());
        Assert.assertEquals(comment.getOnProject().getId(),commentService.findById(comment.getId()).getOnProject().getId());
        Assert.assertEquals(comment.getCommentOwner().getId(),commentService.findById(comment.getId()).getCommentOwner().getId());
        comment = new Comment();
        comment.setCommentOwner(workers.get(1));
        comment.setDate(DateTime.now());
        comment.setOnProject(project);
        comment.setText("Text");
        insert = commentService.insert(comment);

        Assert.assertEquals(commentService.findAll().size(),2);
    }

    @Test
    public void updateComment(){
        if(commentService.findAll().size()==0){
            createComment();
        }
        List<Comment> comments = commentService.findAll();
        Comment comment = comments.get(0);
        comment.setText("Edited text");
        comment = commentService.update(comment);

        Assert.assertEquals(commentService.findById(comment.getId()).getText(),"Edited text");
    }

    @Test
    public void removeComment(){
        if(commentService.findAll().size()==0){
            createComment();
        }
        List<Comment> comments = commentService.findAll();
        for(Comment c : comments) {
            commentService.deleteById(c.getId());
        }

        Assert.assertEquals(0,commentService.findAll().size());

    }


}
