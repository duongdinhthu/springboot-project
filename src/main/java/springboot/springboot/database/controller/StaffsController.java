package springboot.springboot.database.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.springboot.database.entity.*;
import springboot.springboot.database.model.EntityToJSON;
import springboot.springboot.database.model.ModelBuid;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/staffs")
public class StaffsController<T extends Entity<?>>{
    private EntityToJSON json = new EntityToJSON();
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
        Staffs staffs = modelMapper.map(requestData, Staffs.class);
        model.insert(staffs);
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Staffs staffs = modelMapper.map(requestData, Staffs.class);
        model.update(staffs);
    }

    @DeleteMapping("/delete")
    public String delete( @RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Staffs staffs = modelMapper.map(requestData, Staffs.class);
        model.delete(staffs);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = model.getAll(new Staffs().getClass());
        return list;
    }

    @GetMapping("/getById")
    public List<Staffs> getById(@RequestBody Map<String, Object> requestData) throws Exception {
        List<Staffs> staffsList = new ArrayList<>();
        // Truy vấn danh sách bệnh nhân từ cơ sở dữ liệu với id chỉ định
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Staffs staffs1 = modelMapper.map(requestData, Staffs.class);
        List<Staffs> staffs = model.getEntityById(staffs1);
        for (Staffs staff : staffs) {
            Staffs newStaff = new Staffs();
            // Copy dữ liệu từ patient vào newPatient
            BeanUtils.copyProperties(staff, newStaff);
            Appointments appointments = new Appointments();
            appointments.setStaff_id(staff.getStaff_id());
            // Gán danh sách vào các trường list tương ứng với các class
            newStaff.setAppointmentsList(model.getEntityById(appointments));
            staffsList.add(newStaff);
        }
        json.writeEmployeeToJson(staffsList, staffs.getClass(), "getbyfields");
        return staffsList;
    }

    @PostMapping("/insertAll")
    public void insertAll(@RequestBody List<Map<String, Object>> dataList) throws SQLException, IllegalAccessException {
        List<Staffs> staffsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        for (Map<String, Object> data : dataList) {
            Staffs staffs = modelMapper.map(data, Staffs.class);
            staffsList.add(staffs);
        }
        model.insertAll(staffsList);
    }
    public static Object createElementInstance(Class<?> elementType) throws Exception {
        // Kiểm tra xem lớp cụ thể có constructor mặc định không
        Constructor<?> constructor = elementType.getConstructor();
        // Tạo một đối tượng mới thông qua constructor mặc định của lớp cụ thể
        return constructor.newInstance();
    }
}
