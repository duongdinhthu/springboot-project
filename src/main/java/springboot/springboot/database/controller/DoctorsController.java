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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorsController<T extends Entity<?>> {
    @Autowired
    private ModelBuid model = new ModelBuid();
    @PostMapping("/insert")
    public void insert(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Doctors doctors = modelMapper.map(requestData, Doctors.class);
        model.insert(doctors); // Chèn một bản ghi Doctors từ dữ liệu requestData
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Doctors doctors = modelMapper.map(requestData, Doctors.class);
        model.update(doctors);

    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) throws SQLException, IllegalAccessException {
        Doctors doctors = new Doctors();
        doctors.setDoctor_id(id);
        model.delete(doctors);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = model.getAll(new Doctors().getClass());
        return list;
    }

    @GetMapping("/getById")
    public List<Doctors> getById(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        List<Doctors> doctorsList = new ArrayList<>();
        // Truy vấn danh sách bệnh nhân từ cơ sở dữ liệu
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Doctors doctors1 = modelMapper.map(requestData, Doctors.class);
        List<Doctors> doctors =model.getEntityById(doctors1);
        for (Doctors doctor : doctors) {
            // Tạo một bệnh nhân mới cho mỗi vòng lặp
           Doctors newdoctor = new Doctors();
            BeanUtils.copyProperties(doctor, newdoctor);
            Medicalrecords medicalrecords = new Medicalrecords();
            medicalrecords.setDoctor_id(doctor.getDoctor_id());
            List<Medicalrecords> medicalrecordsList = model.getEntityById(medicalrecords);
            Appointments appointment = new Appointments();
            appointment.setDoctor_id(doctor.getDoctor_id());
            List<Appointments> appointmentsList = model.getEntityById(appointment);
            newdoctor.setMedicalrecordsList(medicalrecordsList);
            newdoctor.setAppointmentsList(appointmentsList);
            doctorsList.add(newdoctor);
        }
        return doctorsList;
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