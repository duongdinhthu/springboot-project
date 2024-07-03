package springboot.springboot.database.entity;

import java.util.List;

public class Departments extends Entity<Integer>{
    private Integer department_id;
    private String department_name;
    private String location;
    private List<Doctors> doctorsList;
    public Departments(String department_name, String location) {
        this.department_name = department_name;
        this.location = location;
    }

    public Departments() {
    }

    public Integer getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Integer department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Doctors> getDoctorsList() {
        return doctorsList;
    }

    public void setDoctorsList(List<Doctors> doctorsList) {
        this.doctorsList = doctorsList;
    }
}
