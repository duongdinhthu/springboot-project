package springboot.springboot.database.entity;

import java.math.BigDecimal;
import java.util.List;

public class Orderdetails extends Entity<Integer>{
    private int ordersdetail_id;
    private int orders_id;
    private int product_id;
    private int ordersdetail_quantity;
    private BigDecimal ordersdetail_price;
    private List<Product> productList;

    public Orderdetails(int ordersdetail_id, int orders_id, int product_id, int ordersdetail_quantity, BigDecimal ordersdetail_price, List<Product> productList) {
        this.ordersdetail_id = ordersdetail_id;
        this.orders_id = orders_id;
        this.product_id = product_id;
        this.ordersdetail_quantity = ordersdetail_quantity;
        this.ordersdetail_price = ordersdetail_price;
        this.productList = productList;
    }

    public Orderdetails(int orders_id, int product_id, int ordersdetail_quantity, BigDecimal ordersdetail_price, List<Product> productList) {
        this.orders_id = orders_id;
        this.product_id = product_id;
        this.ordersdetail_quantity = ordersdetail_quantity;
        this.ordersdetail_price = ordersdetail_price;
        this.productList = productList;
    }

    public int getOrdersdetail_id() {
        return ordersdetail_id;
    }

    public void setOrdersdetail_id(int ordersdetail_id) {
        this.ordersdetail_id = ordersdetail_id;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrdersdetail_quantity() {
        return ordersdetail_quantity;
    }

    public void setOrdersdetail_quantity(int ordersdetail_quantity) {
        this.ordersdetail_quantity = ordersdetail_quantity;
    }

    public BigDecimal getOrdersdetail_price() {
        return ordersdetail_price;
    }

    public void setOrdersdetail_price(BigDecimal ordersdetail_price) {
        this.ordersdetail_price = ordersdetail_price;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Orderdetails{" +
                "ordersdetail_id=" + ordersdetail_id +
                ", orders_id=" + orders_id +
                ", product_id=" + product_id +
                ", ordersdetail_quantity=" + ordersdetail_quantity +
                ", ordersdetail_price=" + ordersdetail_price +
                ", productList=" + productList +
                '}';
    }
}
