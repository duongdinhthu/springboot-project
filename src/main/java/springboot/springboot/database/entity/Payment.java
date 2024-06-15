package springboot.springboot.database.entity;

public class Payment extends Entity<Integer>{
    private int payment_id;
    private String payment_name;
    private int patient_id;

    public Payment(String payment_name, int patient_id) {
        this.payment_name = payment_name;
        this.patient_id = patient_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayment_name() {
        return payment_name;
    }

    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
}
