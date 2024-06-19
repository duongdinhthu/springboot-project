package springboot.springboot.database.controller;


import org.modelmapper.ModelMapper;
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
    public void update(@RequestBody Map<String, Object> requestData, @RequestParam String objectType) throws SQLException, IllegalAccessException {
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

    @GetMapping("/getById/{id}")
    public List<Patients> getById(@PathVariable int id) throws SQLException, IllegalAccessException, InstantiationException {
        List<Patients> patientsList = new ArrayList<>();

        // Truy vấn danh sách bệnh nhân từ cơ sở dữ liệu
        Patients patient = new Patients();
        patient.setPatient_id(id);
        List<Patients> patients = model.getEntityById(patient);

        for (Patients patient1 : patients) {
            // Tạo một bệnh nhân mới cho mỗi vòng lặp
            Patients newPatient = new Patients();
            // Gán thông tin cơ bản của bệnh nhân vào bệnh nhân mới
            newPatient.setPatient_id(patient1.getPatient_id());
            newPatient.setPatient_name(patient1.getPatient_name());
            newPatient.setPatient_dob(patient1.getPatient_dob());
            newPatient.setPatient_email(patient1.getPatient_email());
            newPatient.setPatient_phone(patient1.getPatient_phone());
            newPatient.setPatient_address(patient1.getPatient_address());
            newPatient.setPassword(patient1.getPassword());
            newPatient.setUsername(patient1.getUsername());
            Medicalrecords medicalrecords = new Medicalrecords();
            Payment payment = new Payment();
            Appointments appointment = new Appointments();
            payment.setPatient_id(patient1.getPatient_id());
            medicalrecords.setPatient_id(patient1.getPatient_id());
            appointment.setPatient_id(patient1.getPatient_id());
            newPatient.setPatient_id(patient1.getPatient_id());
            // Truy vấn danh sách hẹn cho bệnh nhân từ cơ sở dữ liệu
            List<Payment> payments = model.getEntityById(payment);
            List<Medicalrecords> medicalrecordsList = model.getEntityById(medicalrecords);
            List<Appointments> appointments = model.getEntityById(appointment);

            // Gán danh sách hẹn vào trường AppointmentsList của bệnh nhân
            newPatient.setPaymentList(payments);
            newPatient.setMedicalrecordsList(medicalrecordsList);
            newPatient.setAppointmentsList(appointments);

            patientsList.add(newPatient);
        }

        return patientsList;
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