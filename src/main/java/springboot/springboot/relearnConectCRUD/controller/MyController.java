package springboot.springboot.relearnConectCRUD.controller;

import org.springframework.web.bind.annotation.*;
import springboot.springboot.relearnConectCRUD.entity.Customer;
import springboot.springboot.relearnConectCRUD.entity.Entity;
import springboot.springboot.relearnConectCRUD.model.ModelBuid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Enable CORS for "http://localhost:3000/listCustomer"
public class MyController<T extends Entity<?>> {
    private ModelBuid model = new ModelBuid();

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(String firstName, String lastName, String email, int phone) throws SQLException, IllegalAccessException {
        Entity entity = new Customer(firstName, lastName, email, phone);
        model.insert(entity);
        return "index";
    }

    @PostMapping("/insert")
    public int insert(@RequestBody Entity entity) throws SQLException, IllegalAccessException {
        return model.insert(entity);
    }

    @PutMapping("/update")
    public void update(@RequestBody Entity entity) throws SQLException, IllegalAccessException {
        model.update(entity);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody Entity entity) throws SQLException, IllegalAccessException {
        model.delete(entity);
    }
    @CrossOrigin(origins = "http://localhost:3000/listCustomer") // Explicitly allow CORS for "/list" endpoint
    @GetMapping("/list")
    public List<T> list() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Customer customer = new Customer();
        List<T> list = model.getAll(Customer.class);
        return list;
    }

    @GetMapping("/getById")
    public List<T> getEntityById(@RequestBody Entity entity) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        List<T> entity1 = model.getEntityById(entity);
        return entity1;
    }

    @PostMapping("/insertAll")
    public void insertAll(@RequestBody List<Entity> entities) throws SQLException, IllegalAccessException {
        model.insertAll(entities);
    }
}