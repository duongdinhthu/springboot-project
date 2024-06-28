package springboot.springboot.database.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.entity.*;
import springboot.springboot.database.model.EntityToJSON;
import springboot.springboot.database.model.ModelBuid;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientsController<T extends Entity<?>> {
    @Autowired
    private ModelBuid model = new ModelBuid();
    private EntityToJSON json = new EntityToJSON();

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

    @GetMapping("/getPatients")
    public List<Patients> getById(@RequestParam Map<String, String> requestParams) throws Exception {
        List<Patients> patientsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        // Chuyển đổi Map các tham số truy vấn thành đối tượng Patients
        Patients patients1 = modelMapper.map(requestParams, Patients.class);

        List<Patients> patients = model.getEntityById(patients1);
        for (Patients patient : patients) {
            Patients newPatient = new Patients();
            BeanUtils.copyProperties(patient, newPatient);

            // Lấy danh sách Medicalrecords và Appointments dựa trên patient_id
            Medicalrecords medicalrecordsFilter = new Medicalrecords();
            medicalrecordsFilter.setPatient_id(patient.getPatient_id());
            List<Medicalrecords> medicalrecordsList = model.getEntityById(medicalrecordsFilter);
            List<Medicalrecords> medicalrecords = medicalrecords(medicalrecordsList);

            Appointments appointmentsFilter = new Appointments();
            appointmentsFilter.setPatient_id(patient.getPatient_id());
            List<Appointments> appointmentsList = model.getEntityById(appointmentsFilter);
            List<Appointments> appointments = listAppointments(appointmentsList);

            // Gán danh sách vào các trường list tương ứng với các class
            newPatient.setMedicalrecordsList(medicalrecords);
            newPatient.setAppointmentsList(appointments);
            patientsList.add(newPatient);
        }

        json.writeEmployeeToJson(patientsList, patients.getClass(), "getbyfields");
        return patientsList;
    }
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String patient_username, @RequestParam String patient_password) throws Exception {
        Patients patients1 = new Patients();
        patients1.setPatient_username(patient_username);
        patients1.setPatient_password(patient_password);

        List<Patients> patients = model.getEntityById(patients1);

        if (!patients.isEmpty()) {
            Patients patient = patients.get(0); // Lấy thông tin bệnh nhân đầu tiên (nếu có nhiều)
            return ResponseEntity.ok(Collections.singletonMap("patient_name", patient.getPatient_name()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("success", false));
        }
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
    public static List<String> getChildClassFieldNames(Class<?> parentClass) {
        List<String> childFieldNames = new ArrayList<>();

        Field[] fields = parentClass.getDeclaredFields();

        for (Field field : fields) {
            Class<?> fieldClass = field.getType();
            if (fieldClass != null && !fieldClass.isPrimitive() && fieldClass != String.class && !parentClass.isAssignableFrom(fieldClass) && fieldClass != Date.class) {
                childFieldNames.add(field.getName());

            }
        }
        return childFieldNames;
    }
    public  List<Doctors> listDoctors(List<Doctors> doctorsList) throws SQLException,  InstantiationException, IllegalAccessException {
        List<Doctors> doctors = new ArrayList<>();
        for (Doctors doctor : doctorsList) {
            Doctors newDoctor = new Doctors();
            BeanUtils.copyProperties(doctor, newDoctor);
            Departments departmentsFilter = new Departments();
            departmentsFilter.setDepartment_id(doctor.getDepartment_id());
            newDoctor.setDepartment(model.getEntityById(departmentsFilter));
            doctors.add(newDoctor);
        }
        return doctors;
    }
    public List<Appointments> listAppointments(List<Appointments> appointmentsList) throws SQLException,  InstantiationException, IllegalAccessException {
        List<Appointments> appointments = new ArrayList<>();
        for (Appointments appointment : appointmentsList) {
            Appointments newAppointment = new Appointments();
            BeanUtils.copyProperties(appointment, newAppointment);
            Doctors doctorsFilter = new Doctors();
            doctorsFilter.setDoctor_id(appointment.getDoctor_id());
            List<Doctors> doctorsList = model.getEntityById(doctorsFilter);
            List<Doctors> doctors = listDoctors(doctorsList);
            newAppointment.setDoctor(doctors);
            Staffs staffsFilter = new Staffs();
            staffsFilter.setStaff_id(appointment.getStaff_id());
            newAppointment.setStaff(model.getEntityById(staffsFilter));
            appointments.add(newAppointment);
        }
        return appointments;
    }
    public List<Medicalrecords> medicalrecords(List<Medicalrecords> medicalrecordsList) throws SQLException, IllegalAccessException, InstantiationException {
        List<Medicalrecords> medicalrecords = new ArrayList<>();
        for (Medicalrecords medicalrecord : medicalrecordsList) {
            Medicalrecords newMedicalrecord = new Medicalrecords();
            BeanUtils.copyProperties(medicalrecord, newMedicalrecord);
            Doctors doctorsFilter = new Doctors();
            doctorsFilter.setDoctor_id(medicalrecord.getDoctor_id());
            newMedicalrecord.setDoctors(model.getEntityById(doctorsFilter));
            medicalrecords.add(newMedicalrecord);
        }
        return medicalrecords;
    }
}