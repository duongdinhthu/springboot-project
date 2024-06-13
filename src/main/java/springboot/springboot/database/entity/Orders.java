package springboot.springboot.database.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Orders extends Entity<Integer>{
    private int orders_id;
    private int customer_id;
    private Date orders_date;
    private String orders_payment;
    private BigDecimal orders_price;
    private String orders_status;
    private Date orders_date_stauts;
    private Date orders_date_complete;
    private int orders_discount;
    private int staff_id;
    private List<Orderdetails> orderdetailsList;

    public Orders(Integer id) {
        super(id);
    }

    public Orders() {
    }

    public Orders(int orders_id, int customer_id, Date orders_date, String orders_payment, BigDecimal orders_price, String orders_status, Date orders_date_stauts, Date orders_date_complete, int orders_discount, int staff_id, List<Orderdetails> orderdetailsList) {
        this.orders_id = orders_id;
        this.customer_id = customer_id;
        this.orders_date = orders_date;
        this.orders_payment = orders_payment;
        this.orders_price = orders_price;
        this.orders_status = orders_status;
        this.orders_date_stauts = orders_date_stauts;
        this.orders_date_complete = orders_date_complete;
        this.orders_discount = orders_discount;
        this.staff_id = staff_id;
        this.orderdetailsList = orderdetailsList;
    }

    public Orders(int customer_id, Date orders_date, String orders_payment, BigDecimal orders_price, String orders_status, Date orders_date_stauts, Date orders_date_complete, int orders_discount, int staff_id, List<Orderdetails> orderdetailsList) {
        this.customer_id = customer_id;
        this.orders_date = orders_date;
        this.orders_payment = orders_payment;
        this.orders_price = orders_price;
        this.orders_status = orders_status;
        this.orders_date_stauts = orders_date_stauts;
        this.orders_date_complete = orders_date_complete;
        this.orders_discount = orders_discount;
        this.staff_id = staff_id;
        this.orderdetailsList = orderdetailsList;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public Date getOrders_date() {
        return orders_date;
    }

    public void setOrders_date(Date orders_date) {
        this.orders_date = orders_date;
    }

    public String getOrders_payment() {
        return orders_payment;
    }

    public void setOrders_payment(String orders_payment) {
        this.orders_payment = orders_payment;
    }

    public BigDecimal getOrders_price() {
        return orders_price;
    }

    public void setOrders_price(BigDecimal orders_price) {
        this.orders_price = orders_price;
    }

    public String getOrders_status() {
        return orders_status;
    }

    public void setOrders_status(String orders_status) {
        this.orders_status = orders_status;
    }

    public Date getOrders_date_stauts() {
        return orders_date_stauts;
    }

    public void setOrders_date_stauts(Date orders_date_stauts) {
        this.orders_date_stauts = orders_date_stauts;
    }

    public Date getOrders_date_complete() {
        return orders_date_complete;
    }

    public void setOrders_date_complete(Date orders_date_complete) {
        this.orders_date_complete = orders_date_complete;
    }

    public int getOrders_discount() {
        return orders_discount;
    }

    public void setOrders_discount(int orders_discount) {
        this.orders_discount = orders_discount;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public List<Orderdetails> getOrderdetailsList() {
        return orderdetailsList;
    }

    public void setOrderdetailsList(List<Orderdetails> orderdetailsList) {
        this.orderdetailsList = orderdetailsList;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orders_id=" + orders_id +
                ", customer_id=" + customer_id +
                ", orders_date=" + orders_date +
                ", orders_payment='" + orders_payment + '\'' +
                ", orders_price=" + orders_price +
                ", orders_status='" + orders_status + '\'' +
                ", orders_date_stauts=" + orders_date_stauts +
                ", orders_date_complete=" + orders_date_complete +
                ", orders_discount=" + orders_discount +
                ", staff_account=" + staff_id +
                ", orderdetailsList=" + orderdetailsList +
                '}';
    }
}
