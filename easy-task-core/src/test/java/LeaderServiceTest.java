import com.easytask.model.enums.Role;
import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.Worker;
import com.easytask.service.ILeaderService;
import com.easytask.service.IWorkerService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by Bojan on 6/22/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class LeaderServiceTest {

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IWorkerService workerService;

    private static boolean setupFinished = false;
    private static List<Worker> workers;

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
        w.setUsername("bf_leader");
        w = workerService.insert(w);

        Worker w2 = new Worker();
        w2.setEmail("dummyDummy@mail.com");
        w2.setName("Lorem");
        w2.setPassword("loip");
        w2.setRole(Role.ROLE_USER);
        w2.setSurename("Ipsum");
        w2.setUsername("LorI_leader");
        w2 = workerService.insert(w2);

        workers = new ArrayList<Worker>();
        workers.add(w);
        workers.add(w2);


        setupFinished = true;

    }

    @Test
    public void createDeleteLeader(){
        Leader leader = new Leader();
        leader.setWorker(workers.get(0));
        Leader leader2 = leaderService.insert(leader);
        Worker w = leader2.getWorker();
        Assert.assertEquals(leader2.getId(), leaderService.findById(leader.getId()).getId());
        Assert.assertEquals(leader2.getWorker().getId(), leaderService.findById(leader.getId()).getWorker().getId());

        Long leaderId = leader2.getId();
        leaderService.deleteById(leaderId);
        Assert.assertEquals(null, leaderService.findById(leaderId));

        //deleting @Before objects
        workerService.deleteById(w.getId());
        Assert.assertEquals(null, workerService.findById(w.getId()));
        workerService.deleteById(workers.get(1).getId());
        Assert.assertEquals(null, workerService.findById(workers.get(1).getId()));

    }

    @Test
    public void updateLeader(){

        Leader leader = new Leader();
        leader.setWorker(workers.get(0));
        Leader leader2 = leaderService.insert(leader);

        Assert.assertEquals(leader2.getId(), leaderService.findById(leader.getId()).getId());
        Assert.assertEquals(leader2.getWorker().getId(), leaderService.findById(leader.getId()).getWorker().getId());

        leader2.setWorker(workers.get(1));
        leader2 = leaderService.update(leader2);

        Assert.assertNotEquals(workers.get(0).getId(), leaderService.findById(leader2.getId()).getWorker().getId());
        Assert.assertEquals(workers.get(1).getId(), leaderService.findById(leader2.getId()).getWorker().getId());

        Long leaderId = leader2.getId();
        leaderService.deleteById(leaderId);
        Assert.assertEquals(null, leaderService.findById(leaderId));

    }

    @Test
    public void findAllTest(){
        Leader leader1 = new Leader();
        leader1.setWorker(workers.get(0));
        leader1 = leaderService.insert(leader1);

        Leader leader2 = new Leader();
        leader2.setWorker(workers.get(1));
        leader2 = leaderService.insert(leader2);

        List<Leader> leadersLocal = new ArrayList<Leader>();
        leadersLocal.add(leader1);
        leadersLocal.add(leader2);

        List<Leader> leadersDB = leaderService.findAll();

        List<Long> idLeaders = new ArrayList<Long>();
        Assert.assertEquals(2, leadersDB.size());

        for(Leader ldr : leadersLocal)
            idLeaders.add(ldr.getId());

        for(Leader ldr : leadersDB){
            Assert.assertEquals(true, idLeaders.contains(ldr.getId()));
        }

        leaderService.deleteById(leader1.getId());
        Assert.assertEquals(null, leaderService.findById(leader1.getId()));
        Assert.assertEquals(1, leaderService.findAll().size());

        leaderService.deleteById(leader2.getId());
        Assert.assertEquals(null, leaderService.findById(leader2.getId()));
        Assert.assertEquals(0, leaderService.findAll().size());

    }

}
