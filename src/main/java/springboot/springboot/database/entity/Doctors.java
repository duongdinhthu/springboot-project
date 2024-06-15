package springboot.springboot.database.entity;

import java.util.List;

public class Doctors extends Entity<Integer> {
    private int doctor_id;
    private String doctor_name;
    private int doctor_phone;
    private String doctor_address;
    private String doctor_email;
    private int department_id;
    private String username;
    private String password;
    private List<Appointments> appointmentsList;
    private List<Medicalrecords> medicalrecordsList;
    public Doctors(String doctor_name, int doctor_phone, String doctor_address, String doctor_email, int department_id, String username, String password) {
        this.doctor_name = doctor_name;
        this.doctor_phone = doctor_phone;
        this.doctor_address = doctor_address;
        this.doctor_email = doctor_email;
        this.department_id = department_id;
        this.username = username;
        this.password = password;
    }

    public Doctors() {
    }

    public Doctors(String doctor_name, int doctor_phone, String doctor_address, String doctor_email, int department_id, String username, String password, List<Medicalrecords> medicalrecordsList) {
        this.doctor_name = doctor_name;
        this.doctor_phone = doctor_phone;
        this.doctor_address = doctor_address;
        this.doctor_email = doctor_email;
        this.department_id = department_id;
        this.username = username;
        this.password = password;
        this.medicalrecordsList = medicalrecordsList;
    }

    public Doctors(int doctor_id, String doctor_name, int doctor_phone, String doctor_address, String doctor_email, int department_id, String username, String password, List<Appointments> appointmentsList) {
        this.doctor_id = doctor_id;
        this.doctor_name = doctor_name;
        this.doctor_phone = doctor_phone;
        this.doctor_address = doctor_address;
        this.doctor_email = doctor_email;
        this.department_id = department_id;
        this.username = username;
        this.password = password;
        this.appointmentsList = appointmentsList;
    }

    public Doctors(String doctor_name, int doctor_phone, String doctor_address, String doctor_email, int department_id, String username, String password, List<Appointments> appointmentsList, List<Medicalrecords> medicalrecordsList) {
        this.doctor_name = doctor_name;
        this.doctor_phone = doctor_phone;
        this.doctor_address = doctor_address;
        this.doctor_email = doctor_email;
        this.department_id = department_id;
        this.username = username;
        this.password = password;
        this.appointmentsList = appointmentsList;
        this.medicalrecordsList = medicalrecordsList;
    }

    public List<Appointments> getAppointmentsList() {
        return appointmentsList;
    }

    public void setAppointmentsList(List<Appointments> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    public List<Medicalrecords> getMedicalrecordsList() {
        return medicalrecordsList;
    }

    public void setMedicalrecordsList(List<Medicalrecords> medicalrecordsList) {
        this.medicalrecordsList = medicalrecordsList;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public int getDoctor_phone() {
        return doctor_phone;
    }

    public void setDoctor_phone(int doctor_phone) {
        this.doctor_phone = doctor_phone;
    }

    public String getDoctor_address() {
        return doctor_address;
    }

    public void setDoctor_address(String doctor_address) {
        this.doctor_address = doctor_address;
    }

    public String getDoctor_email() {
        return doctor_email;
    }

    public void setDoctor_email(String doctor_email) {
        this.doctor_email = doctor_email;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
