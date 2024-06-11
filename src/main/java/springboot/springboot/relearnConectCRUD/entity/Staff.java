package springboot.springboot.relearnConectCRUD.entity;

public class Staff extends Entity<Integer>{
    private int staff_id;
    private String username;
    private String password;
    private String account_status;
    private String staff_type;
    private String staff_name;
    private String staff_email;
    private String staff_address;
    private int staff_phone;

    public Staff(int staff_id, String username, String password, String account_status, String staff_type, String staff_name, String staff_email, String staff_address, int staff_phone) {
        this.staff_id = staff_id;
        this.username = username;
        this.password = password;
        this.account_status = account_status;
        this.staff_type = staff_type;
        this.staff_name = staff_name;
        this.staff_email = staff_email;
        this.staff_address = staff_address;
        this.staff_phone = staff_phone;
    }

    public Staff(String username, String password, String account_status, String staff_type, String staff_name, String staff_email, String staff_address, int staff_phone) {
        this.username = username;
        this.password = password;
        this.account_status = account_status;
        this.staff_type = staff_type;
        this.staff_name = staff_name;
        this.staff_email = staff_email;
        this.staff_address = staff_address;
        this.staff_phone = staff_phone;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
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

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public String getStaff_type() {
        return staff_type;
    }

    public void setStaff_type(String staff_type) {
        this.staff_type = staff_type;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getStaff_email() {
        return staff_email;
    }

    public void setStaff_email(String staff_email) {
        this.staff_email = staff_email;
    }

    public String getStaff_address() {
        return staff_address;
    }

    public void setStaff_address(String staff_address) {
        this.staff_address = staff_address;
    }

    public int getStaff_phone() {
        return staff_phone;
    }

    public void setStaff_phone(int staff_phone) {
        this.staff_phone = staff_phone;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staff_id=" + staff_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", account_status='" + account_status + '\'' +
                ", staff_type='" + staff_type + '\'' +
                ", staff_name='" + staff_name + '\'' +
                ", staff_email='" + staff_email + '\'' +
                ", staff_address='" + staff_address + '\'' +
                ", staff_phone=" + staff_phone +
                '}';
    }
}
