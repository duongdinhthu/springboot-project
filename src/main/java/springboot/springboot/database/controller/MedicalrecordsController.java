package springboot.springboot.database.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.entity.*;
import springboot.springboot.database.model.ModelBuid;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/medicalrecords")
public class MedicalrecordsController<T extends Entity<?>>{
    @Autowired
    private ModelBuid model = new ModelBuid();

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Medicalrecords medicalrecords = modelMapper.map(requestData, Medicalrecords.class);
        model.insert(medicalrecords);
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Medicalrecords medicalrecords = modelMapper.map(requestData, Medicalrecords.class);
        model.update(medicalrecords);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Medicalrecords medicalrecords = modelMapper.map(requestData, Medicalrecords.class);
        model.delete(medicalrecords);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = model.getAll(new Medicalrecords().getClass());
        return list;
    }

    @GetMapping("/getById")
    public List<Medicalrecords> getById(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        List<Medicalrecords> medicalrecordsList = new ArrayList<>();
        // Truy vấn danh sách bệnh nhân từ cơ sở dữ liệu với id chỉ định
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Medicalrecords medicalrecords1 = modelMapper.map(requestData, Medicalrecords.class);
        List<Medicalrecords> medicalrecords = model.getEntityById(medicalrecords1);
        for (Medicalrecords medicalrecord : medicalrecords) {
            Medicalrecords newMedicalrecord = new Medicalrecords();
            // Copy dữ liệu từ patient vào newPatient
            BeanUtils.copyProperties(medicalrecord, newMedicalrecord);
            // Gán danh sách vào các trường list tương ứng với các class
            medicalrecordsList.add(newMedicalrecord);
        }
        return medicalrecordsList;
    }

    @PostMapping("/insertAll")
    public void insertAll(@RequestBody List<Entity> entities) throws SQLException, IllegalAccessException {
        model.insertAll(entities);
    }

    public static Object createElementInstance(Class<?> elementType) throws Exception {
        // Kiểm tra xem lớp cụ thể có constructor mặc định không
        Constructor<?> constructor = elementType.getConstructor();
        // Tạo một đối tượng mới thông qua constructor mặc định của lớp cụ thể
        return constructor.newInstance();
    }
}
