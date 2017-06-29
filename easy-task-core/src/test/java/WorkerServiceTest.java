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

}
