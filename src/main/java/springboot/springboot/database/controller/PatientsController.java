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
@RequestMapping("/api/v1/patients")
public class PatientsController<T extends Entity<?>> {
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
        Patients patients = modelMapper.map(requestData, Patients.class);
        model.insert(patients);
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Patients patients = modelMapper.map(requestData, Patients.class);
        model.update(patients);
    }

    @DeleteMapping("/delete")
    public String delete( @RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Patients patients = modelMapper.map(requestData, Patients.class);
        model.delete(patients);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            List<T> list = model.getAll(new Patients().getClass());
            return list;
    }

    @GetMapping("/getById")
    public List<Patients> getById(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        List<Patients> patientsList = new ArrayList<>();
        // Truy vấn danh sách bệnh nhân từ cơ sở dữ liệu với id chỉ định
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Patients patients1 = modelMapper.map(requestData, Patients.class);
        List<Patients> patients = model.getEntityById(patients1);
        for (Patients patient : patients) {
            Patients newPatient = new Patients();
            // Copy dữ liệu từ patient vào newPatient
            BeanUtils.copyProperties(patient, newPatient);
            Medicalrecords medicalrecords = new Medicalrecords();
            medicalrecords.setPatient_id(patient.getPatient_id());
            List<Medicalrecords> medicalrecordsList = model.getEntityById(medicalrecords);
            Appointments appointments = new Appointments();
            appointments.setPatient_id(patient.getPatient_id());
            List<Appointments> appointmentsList = model.getEntityById(appointments);
            // Gán danh sách vào các trường list tương ứng với các class
            newPatient.setMedicalrecordsList(medicalrecordsList);
            newPatient.setAppointmentsList(appointmentsList);
            patientsList.add(newPatient);
        }
        return patientsList;
    }

    @PostMapping("/insertAll")
    public void insertAll(@RequestBody List<Map<String, Object>> dataList) throws SQLException, IllegalAccessException {
        List<Patients> patientsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        for (Map<String, Object> data : dataList) {
            Patients patients = modelMapper.map(data, Patients.class);
            patientsList.add(patients);
        }
        model.insertAll(patientsList);
    }
    public static Object createElementInstance(Class<?> elementType) throws Exception {
        // Kiểm tra xem lớp cụ thể có constructor mặc định không
        Constructor<?> constructor = elementType.getConstructor();
        // Tạo một đối tượng mới thông qua constructor mặc định của lớp cụ thể
        return constructor.newInstance();
    }
}