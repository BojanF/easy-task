import com.easytask.model.enums.Role;
import com.easytask.model.jpa.Worker;
import com.easytask.service.IWorkerService;
import com.easytask.service.impl.WorkerServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Bojan on 6/23/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class WorkerServiceTest {

    @Autowired
    private IWorkerService workerService;


    @Test
    public void createDeleteWorker(){

        Worker w = new Worker();
        w.setEmail("dummy@mail.com");
        w.setName("Filip");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("bf");
        w = workerService.insert(w);

        Assert.assertEquals(w.getId(), workerService.findById(w.getId()).getId());

        workerService.deleteById(w.getId());
        Assert.assertEquals(null, workerService.findById(w.getId()));
    }


    @Test
    public  void updateWorker(){
        Worker w = new Worker();
        w.setEmail("dummy@mail.com");
        w.setName("Filip");
        w.setPassword("pw");
        w.setRole(Role.ROLE_USER);
        w.setSurename("Filipovski");
        w.setUsername("bf");
        w = workerService.insert(w);

        Assert.assertEquals(w.getId(), workerService.findById(w.getId()).getId());

        w.setUsername("BojanF");
        workerService.update(w);



        Worker updated;
        updated = workerService.findById(w.getId());
        Assert.assertNotEquals("bf", updated.getUsername());
        Assert.assertEquals("BojanF", updated.getUsername());

        updated.setEmail("novaMail@mail.com");
        updated.setName("Bojan");
        updated.setSurename("Prezime");
        updated.setPassword("superStrenght");
        updated.setUsername("BojFil");
        updated.setRole(Role.ROLE_ADMIN);
        workerService.update(updated);
        updated = workerService.findById(w.getId());

        Assert.assertNotEquals("BojanF", updated.getUsername());
        Assert.assertEquals("BojFil", updated.getUsername());
        Assert.assertNotEquals("Filip", updated.getName());
        Assert.assertEquals("Bojan", updated.getName());
        Assert.assertNotEquals("dummy@mail.com", updated.getEmail());
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

}
