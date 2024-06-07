package springboot.springboot.relearnConectCRUD.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Orders extends Entity<Integer>{
    private int order_id;
    private int customer_id;
    private Date orderDate;
    private BigDecimal total;

    public Orders(Integer id) {
        super(id);
    }

    public Orders() {
    }

    public Orders(int customer_id, Date orderDate, BigDecimal total) {
        this.customer_id = customer_id;
        this.orderDate = orderDate;
        this.total = total;
    }

    public int getOrder_id() {
        return order_id;
    }

    public Orders(int order_id, int customer_id, Date orderDate, BigDecimal total) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.orderDate = orderDate;
        this.total = total;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", customer_id=" + customer_id +
                ", orderDate=" + orderDate +
                ", total=" + total +
                '}';
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
