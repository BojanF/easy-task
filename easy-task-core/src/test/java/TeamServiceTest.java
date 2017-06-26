import com.easytask.model.enums.Role;
import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.Team;
import com.easytask.model.jpa.Worker;
import com.easytask.service.ILeaderService;
import com.easytask.service.ITeamService;
import com.easytask.service.IWorkerService;
import com.easytask.service.impl.TeamServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    private static boolean setUpfinished = false;
    private static List<Worker> workers;
    private static List<Leader> leaders;

    @Before
    public void createObjects(){
        if(setUpfinished)
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

        workers = new ArrayList<Worker>();
        workers.add(w);
        workers.add(w2);
        workers.add(w3);


        Leader leader = new Leader();
        leader.setWorker(workers.get(0));
        leader = leaderService.insert(leader);

        leaders = new ArrayList<Leader>();
        leaders.add(leader);




        setUpfinished = true;
    }

    @Test
    public void createDeleteTeam(){
        Team team = new Team();
        team.setName("Promo Team");

        Leader teamLeader = leaders.get(0);
        team.setLeader(teamLeader);
        Set<Worker> teamWorkers = new HashSet<Worker>();
        Worker teamWorker = workers.get(1);
        teamWorkers.add(teamWorker);
        team.setWorkers(teamWorkers);;
        Team team2;
        team2 = teamService.insert(team);

        Assert.assertEquals(team2.getId(), teamService.findById(team.getId()).getId());

        teamService.removeAllTeamWorkers(team.getId());
        Set<Worker> wrk = new HashSet<Worker>();
        Assert.assertEquals(wrk, teamService.findById(team.getId()).getWorkers());

        teamService.deleteById(team.getId());
        Assert.assertEquals(null, teamService.findById(team.getId()));

        leaderService.deleteById(teamLeader.getId());
        Assert.assertEquals(null, leaderService.findById(teamLeader.getId()));

        workerService.deleteById(teamWorker.getId());
        workerService.deleteById(teamLeader.getWorker().getId());
        Assert.assertEquals(null, workerService.findById(teamWorker.getId()));
        Assert.assertEquals(null, workerService.findById(teamLeader.getWorker().getId()));

    }

    @Test
    public void addRemoveTeamWorker(){
        Team team = new Team();
        team.setName("Team Oreca");

        Leader teamLeader = leaders.get(0);
        team.setLeader(teamLeader);
        Set<Worker> teamWorkers = new HashSet<Worker>();
        teamWorkers.add(workers.get(0));
        team.setWorkers(teamWorkers);
        Team team2;
        team2 = teamService.insert(team);
        Assert.assertEquals(team2.getId(), teamService.findById(team.getId()).getId());

        //teamService.insertTeamWorker(team2, workers.get(0));
        teamService.insertTeamWorker(team2, workers.get(1));
        teamService.insertTeamWorker(team2, workers.get(2));
        Assert.assertEquals(2, teamService.findById(team2.getId()).getWorkers().size());
        Assert.assertEquals(1, workerService.findById(workers.get(0).getId()).getTeams().size());

        //teamService.removeTeamWorker(team2, workers.get(0));
        //teamService.removeTeamWorker(team2, workers.get(1));
        /*teamService.deleteById(team2.getId());
        leaderService.deleteById(leaders.get(0).getId());
        workerService.deleteById(workers.get(0).getId());
        workerService.deleteById(workers.get(1).getId());*/

    }
}
