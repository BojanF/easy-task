import com.easytask.model.enums.Role;
import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.Worker;
import com.easytask.service.ILeaderService;
import com.easytask.service.IWorkerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
//@ContextConfiguration(locations={"classpath*:test-config.xml"})
public class LeaderServiceTest {

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IWorkerService workerService;

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

        Long x = w2.getId();

        workers = new ArrayList<Worker>();
        workers.add(w);
        workers.add(w2);


        setUpfinished = true;
    }

    @Test
    public void createLeader(){
        Leader leader = new Leader();
        leader.setWorker(workers.get(0));
        Leader leader2;
        leader2 = leaderService.insert(leader);
        Worker w = leader2.getWorker();
        Assert.assertEquals(leader2.getId(), leaderService.findById(leader.getId()).getId());
        Assert.assertEquals(leader2.getWorker().getId(), leaderService.findById(leader.getId()).getWorker().getId());

        leaderService.deleteById(leader2.getId());
        workerService.deleteById(w.getId());
        Assert.assertEquals(null, workerService.findById(w.getId()));

    }


    @Test
    public void deleteLeader(){
        Leader leader = new Leader();
        leader.setWorker(workers.get(1));
        //Leader leader2;
        /*leader2 =*/ leaderService.insert(leader);
        Worker w = leader/*2*/.getWorker();

        Long leaderId = leader/*2*/.getId();
        leaderService.deleteById(leaderId);

        Assert.assertEquals(null, leaderService.findById(leaderId));

        workerService.deleteById(w.getId());
        Assert.assertEquals(null, workerService.findById(w.getId()));

    }
}
