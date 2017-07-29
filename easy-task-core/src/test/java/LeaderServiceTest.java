import com.easytask.model.jpa.Leader;
import com.easytask.model.jpa.User;
import com.easytask.service.ILeaderService;
import com.easytask.service.IUserService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Bojan on 6/22/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class LeaderServiceTest {

    @Autowired
    private ILeaderService leaderService;

    @Autowired
    private IUserService userService;

    private static boolean setupFinished = false;
    private static User user1,user2;

    @Before
    public void createObjects(){

        if(setupFinished)
            return;

        user1 = new User();
        user1.setEmail("dummy@mail.com");
        user1.setName("Filip");
        user1.setPassword("pw");
        user1.setSurename("Filipovski");
        user1.setUsername("bf_leader");
        user1 = userService.insert(user1);

        user2 = new User();
        user2.setEmail("dummyDummy@mail.com");
        user2.setName("Lorem");
        user2.setPassword("loip");
        user2.setSurename("Ipsum");
        user2.setUsername("LorI_leader");
        user2 = userService.insert(user2);

        setupFinished = true;

    }

    @Test
    public void createLeader(){

        Leader leader = new Leader();
        leader.setUser(user1);
        leader = leaderService.insert(leader);

        Assert.assertEquals(leader.getId(), leaderService.findById(leader.getId()).getId());
        Assert.assertEquals(leader.getUser().getId(), leaderService.findById(leader.getId()).getUser().getId());

        leaderService.deleteById(leader.getId());

        Assert.assertEquals(null, leaderService.findById(leader.getId()));

        //TODO delete objects
        //deleting @Before objects
        userService.deleteById(user1.getId());
        Assert.assertEquals(null, userService.findById(user1.getId()));
        userService.deleteById(user2.getId());
        Assert.assertEquals(null, userService.findById(user2.getId()));

    }

    @Test
    public void updateLeader(){

        Leader leader = new Leader();
        leader.setUser(user1);
        leader = leaderService.insert(leader);

        Assert.assertEquals(leader.getUser().getId(), leaderService.findById(leader.getId()).getUser().getId());

        leader.setUser(user2);
        leader = leaderService.update(leader);

        Assert.assertNotEquals(user1, leaderService.findById(leader.getId()).getUser().getId());
        Assert.assertEquals(user2, leaderService.findById(leader.getId()).getUser().getId());

        leaderService.deleteById(leader.getId());

        Assert.assertEquals(null, leaderService.findById(leader.getId()));

    }

    @Test
    public void findAllTest(){

        Leader leader1 = new Leader();
        leader1.setUser(user1);
        leader1 = leaderService.insert(leader1);

        Leader leader2 = new Leader();
        leader2.setUser(user2);
        leader2 = leaderService.insert(leader2);

        Assert.assertEquals(leaderService.findAll().size(),2);

        leaderService.deleteById(leader1.getId());

        Assert.assertEquals(null, leaderService.findById(leader1.getId()));
        Assert.assertEquals(leaderService.findAll().size(), 1);

        leaderService.deleteById(leader2.getId());

        Assert.assertEquals(null, leaderService.findById(leader2.getId()));
        Assert.assertEquals(leaderService.findAll().size(), 0);

    }

}
