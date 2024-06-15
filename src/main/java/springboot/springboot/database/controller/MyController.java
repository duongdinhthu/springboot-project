package springboot.springboot.database.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.entity.Entity;
import springboot.springboot.database.entity.Patients;
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
        if ("patients".equals(objectType)) {
            Patients patients = modelMapper.map(requestData, Patients.class);
            model.insert(patients);
        }
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData, @RequestParam String objectType) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        if ("patients".equals(objectType)) {
            Patients patients = modelMapper.map(requestData, Patients.class);
            model.update(patients);
        }

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id, @RequestParam String objectType) throws SQLException, IllegalAccessException {
        if ("patients".equals(objectType)) {
            Patients patients = new Patients();
            patients.setPatient_id(id);
            model.delete(patients);
            return "success";
        }else {
            return "error";
        }


    }

    // Explicitly allow CORS for "/list" endpoint
    @GetMapping("/list")
    public List<T> list(@RequestParam String objectType) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if ("customer".equals(objectType)) {
            List<T> list = model.getAll(new Patients().getClass());
            return list;
        }  else {
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