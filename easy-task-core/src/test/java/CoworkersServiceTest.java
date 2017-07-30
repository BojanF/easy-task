import com.easytask.model.enums.CoworkerState;
import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.User;
import com.easytask.service.ICoworkersService;
import com.easytask.service.IUserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Marijo on 30-Jul-17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class CoworkersServiceTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICoworkersService coworkersService;

    public static User user1,user2,user3;

    @Before
    public void createObjects(){

        user1 = new User();
        user1.setEmail("dummy@mail.com");
        user1.setName("Filip");
        user1.setPassword("pw");
        user1.setSurname("Filipovski");
        user1.setUsername("ff");
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

    }

    @Test
    public void createCoworkers(){
        //TODO da se sredi haosov
        Set<Coworkers> set = new HashSet<Coworkers>();
        Coworkers coworkers = new Coworkers();
        coworkers.setCoworker(user3);
        set.add(coworkers);
        user1.setCoworkers(set);
        //coworkers = coworkersService.insert(coworkers);

       // List<Coworkers> coworkersList = coworkersService.findAll();

//        Assert.assertEquals(coworkers.getUser().getId(), coworkersList.get(0).getUser().getId());
//        Assert.assertEquals(coworkers.getCoworker().getId(), coworkersList.get(0).getCoworker().getId());
//        Assert.assertEquals(coworkers.getState(), coworkersList.get(0).getState());

       // coworkersService.deleteCoworkers(user1.getId(),user2.getId());

        //Assert.assertEquals(null,coworkersService.findAll());
    }

    @After
    public void deleteObjects(){
        userService.deleteById(user1.getId());
        userService.deleteById(user2.getId());
        userService.deleteById(user3.getId());
    }
}
