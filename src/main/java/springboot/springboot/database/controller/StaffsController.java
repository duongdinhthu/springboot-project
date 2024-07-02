package springboot.springboot.database.controller;

import springboot.springboot.database.entity.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springboot.springboot.database.model.EntityToJSON;
import springboot.springboot.database.model.ModelBuid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/staffs")
public class StaffsController<T extends Entity<?>> {

    @Autowired
    private ModelBuid model;
    private EntityToJSON json = new EntityToJSON();

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
        staffs.setAppointmentsList(null);
        model.update(staffs);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Map<String, Object> requestData) throws SQLException, IllegalAccessException {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());
        Staffs staffs = modelMapper.map(requestData, Staffs.class);
        model.delete(staffs);
        return "success";
    }

    @GetMapping("/list")
    public List<T> list() throws SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return model.getAll(new Staffs().getClass());
    }

    @GetMapping("/search")
    public List<Staffs> getByField(@RequestParam Map<String, String> requestParams) throws Exception {
        List<Staffs> staffsList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new StringToDateConverter());

        Staffs staffs1 = modelMapper.map(requestParams, Staffs.class);
        List<Staffs> staffs = model.getEntityById(staffs1);

        for (Staffs staff : staffs) {
            Staffs newStaff = new Staffs();
            BeanUtils.copyProperties(staff, newStaff);

            Appointments appointmentsFilter = new Appointments();
            appointmentsFilter.setStaff_id(staff.getStaff_id());
            List<Appointments> appointmentsList = model.getEntityById(appointmentsFilter);
            List<Appointments> appointments = listAppointments(appointmentsList);

            newStaff.setAppointmentsList(appointments);
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

    @GetMapping("/{staffId}/appointments")
    public ResponseEntity<List<Appointments>> getAppointmentsByStaffId(@PathVariable int staffId) throws SQLException, IllegalAccessException, InstantiationException {
        Appointments appointmentsFilter = new Appointments();
        appointmentsFilter.setStaff_id(staffId);
        List<Appointments> appointmentsList = model.getEntityById(appointmentsFilter);
        return ResponseEntity.ok(listAppointments(appointmentsList));
    }

    public List<Appointments> listAppointments(List<Appointments> appointmentsList) throws SQLException, InstantiationException, IllegalAccessException {
        List<Appointments> appointments = new ArrayList<>();
        for (Appointments appointment : appointmentsList) {
            Appointments newAppointment = new Appointments();
            BeanUtils.copyProperties(appointment, newAppointment);

            Doctors doctorsFilter = new Doctors();
            doctorsFilter.setDoctor_id(appointment.getDoctor_id());
            List<Doctors> doctorsList = model.getEntityById(doctorsFilter);
            List<Doctors> doctors = listDoctors(doctorsList);
            newAppointment.setDoctor(doctors);

            Patients patientsFilter = new Patients();
            patientsFilter.setPatient_id(appointment.getPatient_id());
            newAppointment.setPatient(model.getEntityById(patientsFilter));

            appointments.add(newAppointment);
        }
        return appointments;
    }

    public List<Doctors> listDoctors(List<Doctors> doctorsList) throws SQLException, InstantiationException, IllegalAccessException {
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
}
