package springboot.springboot.database.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Appointments extends Entity<Integer>{
    private int appointment_id;
    private int patient_id;
    private int doctor_id;
    private Date appointment_date;
    private Date medical_day;
    private int slot;
    private String status;
    private String payment_name;
    private BigDecimal price;
    private int staff_id;
    private List<Patients> patient;
    private List<Doctors> doctor;
    private List<Staffs> staff;
    public Appointments() {
    }

    public List<Patients> getPatient() {
        return patient;
    }

    public void setPatient(List<Patients> patient) {
        this.patient = patient;
    }

    public List<Doctors> getDoctor() {
        return doctor;
    }

    public void setDoctor(List<Doctors> doctor) {
        this.doctor = doctor;
    }

    public List<Staffs> getStaff() {
        return staff;
    }

    public void setStaff(List<Staffs> staff) {
        this.staff = staff;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Date getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(Date appointment_date) {
        this.appointment_date = appointment_date;
    }

    public Date getMedical_day() {
        return medical_day;
    }

    public void setMedical_day(Date medical_day) {
        this.medical_day = medical_day;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
