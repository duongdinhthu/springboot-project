package springboot.springboot.database.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.entity.Customer;
import springboot.springboot.database.entity.Entity;
import springboot.springboot.database.entity.Orders;
import springboot.springboot.database.entity.Product;
import springboot.springboot.database.model.ModelBuid;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MyController<T extends Entity<?>> {
    @Autowired
    private ModelBuid model = new ModelBuid();

    @GetMapping("/")
    public String showForm() {
        return "index";
    }


    @PostMapping("/insert")
    public void insert(@RequestBody Map<String, Object> requestData, @RequestParam String objectType) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        if ("customer".equals(objectType)) {
            Customer customer = modelMapper.map(requestData, Customer.class);
            model.insert(customer);
        }
    }

    @PutMapping("/update")
    public void update(@RequestBody Entity entity, @RequestParam String objectType) throws SQLException, IllegalAccessException {
        if ("customer".equals(objectType)) {
            Customer customer = new Customer();
            model.update(entity);
        }

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id, @RequestParam String objectType) throws SQLException, IllegalAccessException {
        if ("customer".equals(objectType)) {
            Customer customer = new Customer();
            customer.setCustomer_id(id);
            model.delete(customer);
            return "success";
        } else if ("product".equals(objectType)) {
            Product product = new Product();
            product.setProduct_id(id);
            model.delete(product);
            return "success";
        } else if ("orders".equals(objectType)) {
            Orders order = new Orders();
            order.setCustomer_id(id);
            model.delete(order);
            return "success";
        }

        return "failed";
    }

    // Explicitly allow CORS for "/list" endpoint
    @GetMapping("/list")
    public List<T> list(@RequestParam String objectType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if ("customer".equals(objectType)) {
            List<T> list = model.getAll(new Customer().getClass());
            return list;
        } else if ("product".equals(objectType)) {
            List<T> list = model.getAll(new Product().getClass());
            return list;
        } else {
            // Xử lý khi loại đối tượng không đúng
            return null;
        }
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