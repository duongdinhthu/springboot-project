package springboot.springboot.database.entity;

import java.util.Date;
import java.util.List;

public class Patients extends Entity<Integer> {
    private Integer patient_id;
    private String patient_name;
    private Date patient_dob;
    private String patient_email;
    private Integer patient_phone;
    private String patient_address;
    private String password;
    private String username;
    private List<Appointments> appointmentsList;
    private List<Medicalrecords> medicalrecordsList;
    private List<Payment> paymentList;
    public Patients(String patient_name, Date patient_dob, String patient_email, Integer patient_phone, String patient_address, String password, String username) {
        this.patient_name = patient_name;
        this.patient_dob = patient_dob;
        this.patient_email = patient_email;
        this.patient_phone = patient_phone;
        this.patient_address = patient_address;
        this.password = password;
        this.username = username;
    }

    public Patients() {
    }

    public Patients(String patient_name, Date patient_dob, String patient_email, Integer patient_phone, String patient_address, String password, String username, List<Appointments> appointmentsList, List<Medicalrecords> medicalrecordsList) {
        this.patient_name = patient_name;
        this.patient_dob = patient_dob;
        this.patient_email = patient_email;
        this.patient_phone = patient_phone;
        this.patient_address = patient_address;
        this.password = password;
        this.username = username;
        this.appointmentsList = appointmentsList;
        this.medicalrecordsList = medicalrecordsList;
    }

    public Patients(String patient_name, Date patient_dob, String patient_email, Integer patient_phone, String patient_address, String password, String username, List<Appointments> appointmentsList, List<Medicalrecords> medicalrecordsList, List<Payment> paymentList) {
        this.patient_name = patient_name;
        this.patient_dob = patient_dob;
        this.patient_email = patient_email;
        this.patient_phone = patient_phone;
        this.patient_address = patient_address;
        this.password = password;
        this.username = username;
        this.appointmentsList = appointmentsList;
        this.medicalrecordsList = medicalrecordsList;
        this.paymentList = paymentList;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
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

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public Date getPatient_dob() {
        return patient_dob;
    }

    public void setPatient_dob(Date patient_dob) {
        this.patient_dob = patient_dob;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public Integer getPatient_phone() {
        return patient_phone;
    }

    public void setPatient_phone(Integer patient_phone) {
        this.patient_phone = patient_phone;
    }

    public String getPatient_address() {
        return patient_address;
    }

    public void setPatient_address(String patient_address) {
        this.patient_address = patient_address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
