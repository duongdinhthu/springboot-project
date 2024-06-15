package springboot.springboot.database.entity;

import java.util.Date;

public class Appointments extends Entity<Integer>{
    private int appointment_id;
    private int patient_id;
    private int doctor_id;
    private Date appointment_date;
    private Date medical_day;
    private int slot;
    private String status;

    public Appointments(int patient_id, int doctor_id, Date appointment_date, Date medical_day, int slot, String status) {
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.appointment_date = appointment_date;
        this.medical_day = medical_day;
        this.slot = slot;
        this.status = status;
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
