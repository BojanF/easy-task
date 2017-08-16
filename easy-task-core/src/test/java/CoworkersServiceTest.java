import com.easytask.model.enums.CoworkerState;
import com.easytask.model.jpa.Coworkers;
import com.easytask.model.jpa.CoworkerId;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static User user1, user2, user3, user4, user5, user6;

    @Before
    public void createObjects(){

        user1 = new User();
        user1.setEmail("mail1@mail.com");
        user1.setName("Bojan");
        user1.setPassword("pw");
        user1.setSurname("Filipovski");
        user1.setUsername("ff");
        user1 = userService.insert(user1);

        user2 = new User();
        user2.setEmail("mail2@mail.com");
        user2.setName("Lorem");
        user2.setPassword("loip");
        user2.setSurname("Ipsum");
        user2.setUsername("LorI");
        user2 = userService.insert(user2);

        user3 = new User();
        user3.setEmail("mail3@mail.com");
        user3.setName("John");
        user3.setPassword("joed");
        user3.setSurname("Doe");
        user3.setUsername("JD");
        user3 = userService.insert(user3);

        user4 = new User();
        user4.setEmail("mail4@mail.com");
        user4.setName("Igor");
        user4.setPassword("iggy");
        user4.setSurname("Dzambazov");
        user4.setUsername("IggyJumbo");
        user4 = userService.insert(user4);

        user5 = new User();
        user5.setEmail("mail5@mail.com");
        user5.setName("User5 name");
        user5.setPassword("u5pw");
        user5.setSurname("User5 surname");
        user5.setUsername("UN5");
        user5 = userService.insert(user5);

        user6 = new User();
        user6.setEmail("mail6@mail.com");
        user6.setName("User6 name");
        user6.setPassword("u6pw");
        user6.setSurname("User6 surname");
        user6.setUsername("UN6");
        user6 = userService.insert(user6);

    }

    @Test
    public void createDeleteCoworkers(){

        Coworkers coworkers1 = new Coworkers();
        coworkers1.setId(new CoworkerId(user1.getId(), user3.getId()));
        coworkers1.setUserA(user1);
        coworkers1.setUserB(user3);
        coworkers1.setState(CoworkerState.ACCEPTED);
        coworkers1 = coworkersService.insert(coworkers1);

        //test that coworker is is persisted in db
        //test for findById and insert function
        Coworkers getByIdCoworkers = coworkersService.findById(coworkers1.getId());
        Assert.assertEquals(coworkers1.getId(), getByIdCoworkers.getId());

        //deleting coworker
        //test deleteById function
        coworkersService.deleteById(coworkers1.getId());
        Assert.assertEquals(null, coworkersService.findById(coworkers1.getId()));
        //Assert.assertEquals(null, coworkersService.findById(new CoworkerId(user3.getId(), user1.getId())));

    }

    @Test
    public void updateCoworkers(){
        Coworkers coworkers2 = new Coworkers();
        coworkers2.setId(new CoworkerId(user5.getId(), user6.getId()));
        coworkers2.setUserA(user5);
        coworkers2.setUserB(user6);
        coworkers2.setState(CoworkerState.PENDING);
        coworkers2 = coworkersService.insert(coworkers2);

        //test that coworker request has state PENDING
        Assert.assertEquals(CoworkerState.PENDING, coworkersService.findById(coworkers2.getId()).getState());

        //updating state to ACCEPTED
        coworkers2.setState(CoworkerState.ACCEPTED);
        coworkers2 = coworkersService.update(coworkers2);

        //test that coworker request has state ACCEPTED
        Assert.assertEquals(CoworkerState.ACCEPTED, coworkersService.findById(coworkers2.getId()).getState());

        //deleting coworker
        //test deleteById function
        coworkersService.deleteById(coworkers2.getId());
        Assert.assertEquals(null, coworkersService.findById(coworkers2.getId()));


    }

    @Test
    public void testCoworkersForUser(){

        //creating coworkers for user1
        Coworkers coworkers1 = new Coworkers();
        coworkers1.setId(new CoworkerId(user1.getId(), user3.getId()));
        coworkers1.setUserA(user1);
        coworkers1.setUserB(user3);
        coworkers1.setState(CoworkerState.ACCEPTED);
        coworkers1 = coworkersService.insert(coworkers1);

        Coworkers coworkers2 = new Coworkers();
        coworkers2.setId(new CoworkerId(user1.getId(), user2.getId()));
        coworkers2.setUserA(user1);
        coworkers2.setUserB(user2);
        coworkers2.setState(CoworkerState.PENDING);
        coworkers2 = coworkersService.insert(coworkers2);

        Coworkers coworkers3 = new Coworkers();
        coworkers3.setId(new CoworkerId(user1.getId(), user4.getId()));
        coworkers3.setUserA(user1);
        coworkers3.setUserB(user4);
        coworkers3.setState(CoworkerState.ACCEPTED);
        coworkers3 = coworkersService.insert(coworkers3);

        //fetching coworkers for user1
        //test for getCoworkersForUser function
        List<User> coworkersForUser1 = coworkersService.getCoworkersForUser(user1.getId());
        Assert.assertEquals(2, coworkersForUser1.size());

        //testing that user3 and user4 are coworkers with user1
        List<Long> identifiersCoworkersForUser1 = Arrays.asList(user3.getId(), user4.getId());
        for(User u : coworkersForUser1){
            Assert.assertEquals(true, identifiersCoworkersForUser1.contains(u.getId()));
        }

        //fetching users that have status PENDING with user1
        //user1 sent requests
        List<User> user1SentRequests = coworkersService.getCoworkerRequestsSent(user1.getId());
        //test that user2 has PENDING status with user1
        Assert.assertEquals(1, user1SentRequests.size());
        Assert.assertEquals(user2.getId(), user1SentRequests.get(0).getId());

        //test that user1 does not have received coworker request
        List<User> user1ReceivedRequests = coworkersService.getCoworkerRequestsReceived(user1.getId());
        Assert.assertEquals(0, user1ReceivedRequests.size());

        //testing that user2 has coworker request from user1
        List<User> user2ReceivedRequests = coworkersService.getCoworkerRequestsReceived(user2.getId());
        Assert.assertEquals(1, user2ReceivedRequests.size());
        Assert.assertEquals(user1.getId(), user2ReceivedRequests.get(0).getId());

        //test that user2 does not have sent coworker requests
        List<User> user2SentRequests = coworkersService.getCoworkerRequestsSent(user2.getId());
        Assert.assertEquals(0, user2SentRequests.size());


        //fetching users that does not have any coworker status with user1
        //a.k.a. potential coworkers for user1
        List<User> nonEngagedUsersForUser1 = coworkersService.getNonEngagedUsersForUser(user1.getId());
        Assert.assertEquals(2, nonEngagedUsersForUser1.size());
        //test that user5 & user6 are only potential coworkers for user1
        List<Long> identifiersNonEngagedUsersForUser1 = Arrays.asList(user5.getId(), user6.getId());
        for(User u : nonEngagedUsersForUser1)
            Assert.assertEquals(true, identifiersNonEngagedUsersForUser1.contains(u.getId()));



        //creating coworkers for user4
        Coworkers coworkers4 = new Coworkers();
        coworkers4.setId(new CoworkerId(user4.getId(), user3.getId()));
        coworkers4.setUserA(user4);
        coworkers4.setUserB(user3);
        coworkers4.setState(CoworkerState.ACCEPTED);
        coworkers4 = coworkersService.insert(coworkers4);

        //fetching coworkers for user3
        List<User> coworkersForUser3 = coworkersService.getCoworkersForUser(user3.getId());
        //test that there are 2 coworkers for user3
        Assert.assertEquals(2, coworkersForUser3.size());
        //fetching users that does not have any coworker status with user3
        List<User> nonEngagedUsersForUser3 = coworkersService.getNonEngagedUsersForUser(user3.getId());
        //test that only 3 users are potential coworkers for user3
        Assert.assertEquals(3, nonEngagedUsersForUser3.size());
        //testing that user2, user5 and user6 are real potential coworkers for user3
        List<Long> identifiersNonEngagedUsersForUser3 = Arrays.asList(user2.getId(), user5.getId(), user6.getId());
        for(User u : nonEngagedUsersForUser3)
            Assert.assertEquals(true, identifiersNonEngagedUsersForUser3.contains(u.getId()));

        //fetching coworkers for user4
        //test for getCoworkersForUser function
        List<User> coworkersForUser4 = coworkersService.getCoworkersForUser(user4.getId());
        Assert.assertEquals(2, coworkersForUser4.size());
        //testing that user1 and user3 are coworkers for user4
        List<Long> identifiersCoworkersForUser4 = Arrays.asList(user1.getId(), user3.getId());
        for(User u : coworkersForUser4)
            Assert.assertEquals(true, identifiersCoworkersForUser4.contains(u.getId()));


        //fetching users that doesn`t have any coworker status with user4
        //a.k.a. potential coworkers for user4
        List<User> nonEngagedUsersForUser4 = coworkersService.getNonEngagedUsersForUser(user4.getId());
        Assert.assertEquals(3, nonEngagedUsersForUser4.size());
        //testing that user1, user2 and user5 are real potential coworkers for user4
        List<Long> identifiersNonEngagedUsersForUser4 = Arrays.asList(user2.getId(), user5.getId(), user6.getId());
        for(User u : nonEngagedUsersForUser4)
            Assert.assertEquals(true, identifiersNonEngagedUsersForUser4.contains(u.getId()));


        //findAll function test
        List<Coworkers> allCoworkersRecords = coworkersService.findAll();
        Assert.assertEquals(4, allCoworkersRecords.size());

        //deleting coworkers for user1
        //test for deleteById & findById
        coworkersService.deleteById(coworkers1.getId());
        coworkersService.deleteById(coworkers2.getId());
        coworkersService.deleteById(coworkers3.getId());
        Assert.assertEquals(null, coworkersService.findById(coworkers1.getId()));
        Assert.assertEquals(null, coworkersService.findById(coworkers2.getId()));
        Assert.assertEquals(null, coworkersService.findById(coworkers3.getId()));
        //deleting coworkers for user4
        //test for deleteById & findById
        coworkersService.deleteById(coworkers4.getId());
        Assert.assertEquals(null, coworkersService.findById(coworkers4.getId()));
    }

    @After
    public void deleteObjects(){
        userService.deleteById(user1.getId());
        userService.deleteById(user2.getId());
        userService.deleteById(user3.getId());
        userService.deleteById(user4.getId());
        userService.deleteById(user5.getId());
        userService.deleteById(user6.getId());
    }
}
