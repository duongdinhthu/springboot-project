package springboot.springboot.database.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.model.EntityToJSON;
import springboot.springboot.database.model.ModelBuid;
import springboot.springboot.database.entity.Departments;
import springboot.springboot.database.entity.Doctors;
import springboot.springboot.database.entity.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/departments")

public class DepartmentsController<T extends Entity<?>> {
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
        Departments departments = modelMapper.map(requestData, Departments.class);
        model.insert(departments);
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Departments departments = modelMapper.map(requestData, Departments.class);
        model.update(departments);
    }

    @DeleteMapping("/delete")
    public String delete( @RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Departments departments = modelMapper.map(requestData, Departments.class);
        model.delete(departments);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = model.getAll(new Departments().getClass());
        return list;
    }

    @GetMapping("/getById")
    public List<Departments> getById(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        List<Departments> departmentsList = new ArrayList<>();
        // Truy vấn danh sách bệnh nhân từ cơ sở dữ liệu với id chỉ định
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Departments departments1 = modelMapper.map(requestData, Departments.class);
        List<Departments> departments = model.getEntityById(departments1);
        for (Departments department : departments) {
            Departments newDepartment = new Departments();
            // Copy dữ liệu từ patient vào newPatient
            BeanUtils.copyProperties(department, newDepartment);
            Doctors doctors = new Doctors();
            doctors.setDepartment_id(department.getDepartment_id());
            // Gán danh sách vào các trường list tương ứng với các class
            newDepartment.setDoctorsList(model.getEntityById(doctors));
            departmentsList.add(newDepartment);
        }
        return departmentsList;
    }

    @GetMapping("/{departmentId}/doctors")
    public List<Doctors> getDoctorsByDepartmentId(@PathVariable("departmentId") int departmentId) throws SQLException, IllegalAccessException, InstantiationException {
        List<Doctors> doctorsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        Doctors doctors1 = new Doctors();
        doctors1.setDepartment_id(departmentId);

        List<Doctors> doctors = model.getEntityById(doctors1);
        for (Doctors doctor : doctors) {
            Doctors newDoctor = new Doctors();
            BeanUtils.copyProperties(doctor, newDoctor);

            // Chỉ lấy thông tin cơ bản của bác sĩ, không lấy các thông tin liên quan khác
            newDoctor.setMedicalrecordsList(null);
            newDoctor.setAppointmentsList(null);
            newDoctor.setDepartment(null);

            doctorsList.add(newDoctor);
        }
        return doctorsList;
    }


    @PostMapping("/insertAll")
    public void insertAll(@RequestBody List<Map<String, Object>> dataList) throws SQLException, IllegalAccessException {
        List<Departments> departmentsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        for (Map<String, Object> data : dataList) {
            Departments departments = modelMapper.map(data, Departments.class);
            departmentsList.add(departments);
        }
        model.insertAll(departmentsList);
    }

    public static Object createElementInstance(Class<?> elementType) throws Exception {
        // Kiểm tra xem lớp cụ thể có constructor mặc định không
        Constructor<?> constructor = elementType.getConstructor();
        // Tạo một đối tượng mới thông qua constructor mặc định của lớp cụ thể
        return constructor.newInstance();
    }
}
