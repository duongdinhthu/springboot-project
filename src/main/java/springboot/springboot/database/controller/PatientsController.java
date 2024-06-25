package springboot.springboot.database.controller;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springboot.springboot.database.entity.*;
import springboot.springboot.database.model.EntityToJSON;
import springboot.springboot.database.model.ModelBuid;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/getById")
    public List<Patients> getById(@RequestBody Map<String, Object> requestData) throws Exception {
        List<Patients> patientsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Patients patients1 = modelMapper.map(requestData, Patients.class);
        List<Patients> patients = model.getEntityById(patients1);
        for (Patients patient : patients) {
            Patients newPatient = new Patients();
            BeanUtils.copyProperties(patient, newPatient);
            // Lấy danh sách Medicalrecords và Appointments dựa trên patient_id
            Medicalrecords medicalrecordsFilter = new Medicalrecords();
            medicalrecordsFilter.setPatient_id(patient.getPatient_id());
            List<Medicalrecords> medicalrecordsList = model.getEntityById(medicalrecordsFilter);
            Appointments appointmentsFilter = new Appointments();
            appointmentsFilter.setPatient_id(patient.getPatient_id());
            List<Appointments> appointmentsList = model.getEntityById(appointmentsFilter);

            // Gán danh sách vào các trường list tương ứng với các class
            newPatient.setMedicalrecordsList(medicalrecordsList);
            newPatient.setAppointmentsList(appointmentsList);

            patientsList.add(newPatient);
        }
        json.writeEmployeeToJson(patientsList, patients.getClass(), "getbyfields");
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
    public static List<String> getChildClassFieldNames(Class<?> parentClass) {
        List<String> childFieldNames = new ArrayList<>();

        Field[] fields = parentClass.getDeclaredFields();

        for (Field field : fields) {
            Class<?> fieldClass = field.getType();
            if (fieldClass != null && !fieldClass.isPrimitive() && fieldClass != String.class && !parentClass.isAssignableFrom(fieldClass) && fieldClass != Date.class) {
                childFieldNames.add(field.getName());
                System.out.println(field.getName());
            }
        }

        return childFieldNames;
    }
}