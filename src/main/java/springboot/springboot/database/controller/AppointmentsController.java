package springboot.springboot.database.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.springboot.database.entity.*;
import springboot.springboot.database.model.EntityToJSON;
import springboot.springboot.database.model.ModelBuid;
import springboot.springboot.database.model.SendEmailUsername;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentsController<T extends Entity<?>> {

    private EntityToJSON json = new EntityToJSON();
    private SendEmailUsername sendEmail = new SendEmailUsername();

    @Autowired
    private ModelBuid model;

    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    @PostMapping("/insert")
    public void insert(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException, InstantiationException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        // Map requestData to Patients
        Patients patient = modelMapper.map(requestData, Patients.class);

        // Check if patient exists
        Patients patientExample = new Patients();
        patientExample.setPatient_email(patient.getPatient_email());
        List<Patients> existingPatients = model.getEntityById(patientExample);

        Integer patientId;
        if (existingPatients.isEmpty()) {
            // Insert new patient if not exists
            patientId = model.insert(patient);

            // Get patient_name, patient_email, and patient_password from requestData
            String patientName = (String) requestData.get("patient_name");
            String patientEmail = (String) requestData.get("patient_email");
            String patientPassword = (String) requestData.get("patient_password");

            // Send email with account information
            sendEmail.sendEmail(patientName, patientEmail, patientPassword);

        } else {
            patientId = existingPatients.get(0).getPatient_id();
        }

        // Map requestData to Appointments
        Appointments appointments = modelMapper.map(requestData, Appointments.class);
        appointments.setPatient_id(patientId);
        model.insert(appointments);
    }

    @PutMapping("/update")
    public void update(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Appointments appointments = modelMapper.map(requestData, Appointments.class);
        model.update(appointments);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Appointments appointments = modelMapper.map(requestData, Appointments.class);
        model.delete(appointments);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        return model.getAll(new Appointments().getClass());
    }

    @GetMapping("/search")
    public List<Appointments> getByField(@RequestParam Map<String, String> requestParams) {
        try {
            List<Appointments> appointmentsList = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.addConverter(new StringToDateConverter());

            Appointments appointments1 = modelMapper.map(requestParams, Appointments.class);
            List<Appointments> appointments = model.getEntityById(appointments1);

            for (Appointments appointment : appointments) {
                Appointments newAppointment = new Appointments();
                BeanUtils.copyProperties(appointment, newAppointment);

                if (appointment.getStaff_id() != null) {
                    Staffs staffsFilter = new Staffs();
                    staffsFilter.setStaff_id(appointment.getStaff_id());
                    newAppointment.setStaff(model.getEntityById(staffsFilter));
                }

                if (appointment.getDoctor_id() != null) {
                    Doctors doctorsFilter = new Doctors();
                    doctorsFilter.setDoctor_id(appointment.getDoctor_id());
                    newAppointment.setDoctor(model.getEntityById(doctorsFilter));
                }

                if (appointment.getPatient_id() != null) {
                    Patients patientsFilter = new Patients();
                    patientsFilter.setPatient_id(appointment.getPatient_id());
                    newAppointment.setPatient(model.getEntityById(patientsFilter));
                }

                appointmentsList.add(newAppointment);
            }

            json.writeEmployeeToJson(appointmentsList, appointments.getClass(), "getbyfields");
            return appointmentsList;
        } catch (Exception e) {
            // Log the exception and return an appropriate error response
            e.printStackTrace();
            return new ArrayList<>(); // or return a custom error response
        }
    }


    @GetMapping("/{doctorId}/slots")
    public List<Appointments> getAppointmentsByDoctorId(@PathVariable("doctorId") int doctorId) throws SQLException, IllegalAccessException, InstantiationException {
        Appointments example = new Appointments();
        example.setDoctor_id(doctorId);
        List<Appointments> appointmentsList = new ArrayList<>();
        List<Appointments> appointments = model.getEntityById(example);

        for (Appointments appointment : appointments) {
            Appointments newAppointment = new Appointments();
            BeanUtils.copyProperties(appointment, newAppointment, "patient_id", "staff_id", "price", "payment_name", "status");
            appointmentsList.add(newAppointment);
        }

        return appointmentsList;
    }

    @PostMapping("/insertAll")
    public void insertAll(@RequestBody List<Map<String, Object>> dataList) throws SQLException, IllegalAccessException {
        List<Appointments> appointmentsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        for (Map<String, Object> data : dataList) {
            Appointments appointments = modelMapper.map(data, Appointments.class);
            appointmentsList.add(appointments);
        }
        model.insertAll(appointmentsList);
    }

    public static Object createElementInstance(Class<?> elementType) throws Exception {
        Constructor<?> constructor = elementType.getConstructor();
        return constructor.newInstance();
    }
    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        // Lấy thông tin appointment từ requestData
        Appointments appointments = modelMapper.map(requestData, Appointments.class);

        // Chuyển đổi staff_id từ String sang Integer
        if (requestData.containsKey("staff_id")) {
            try {
                Integer staffId = Integer.valueOf((String) requestData.get("staff_id"));
                appointments.setStaff_id(staffId);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid staff_id format");
            }
        }

        model.update(appointments);
        return ResponseEntity.ok("Appointment status updated successfully");
    }

}
