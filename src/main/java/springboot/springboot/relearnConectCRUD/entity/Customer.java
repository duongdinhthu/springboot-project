package springboot.springboot.relearnConectCRUD.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Customer extends Entity<Integer> {
    private int customer_id;
    private String customer_name;
    private String customer_email;
    private int customer_phone;
    private String customer_address;
    private String customer_classification;
    private BigDecimal total_value;
    private String username;
    private String password;
    private Date date_created;

    public Customer() {
    }

    public Customer(int customer_id, String customer_name, String customer_email, int customer_phone, String customer_address, String customer_classification, BigDecimal total_value, String username, String password, Date date_created) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.customer_address = customer_address;
        this.customer_classification = customer_classification;
        this.total_value = total_value;
        this.username = username;
        this.password = password;
        this.date_created = date_created;
    }

    public Customer(String customer_name, String customer_email, int customer_phone, String customer_address, String customer_classification, BigDecimal total_value, String username, String password, Date date_created) {
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.customer_address = customer_address;
        this.customer_classification = customer_classification;
        this.total_value = total_value;
        this.username = username;
        this.password = password;
        this.date_created = date_created;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public int getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(int customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_classification() {
        return customer_classification;
    }

    public void setCustomer_classification(String customer_classification) {
        this.customer_classification = customer_classification;
    }

    public BigDecimal getTotal_value() {
        return total_value;
    }

    public void setTotal_value(BigDecimal total_value) {
        this.total_value = total_value;
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

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", customer_email='" + customer_email + '\'' +
                ", customer_phone=" + customer_phone +
                ", customer_address='" + customer_address + '\'' +
                ", customer_classification='" + customer_classification + '\'' +
                ", total_value=" + total_value +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", date_created=" + date_created +
                '}';
    }
}
