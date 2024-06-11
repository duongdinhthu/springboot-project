package springboot.springboot.relearnConectCRUD.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Product extends Entity<Integer> {
    private int product_id;
    private String product_name;
    private String product_description;
    private BigDecimal product_price;
    private String product_classification;
    private int product_quantity;
    private Date date_created;
    private int category_id;
    private List<Imgproduct> imgproductList;

    public int getProduct_id() {
        return product_id;
    }

    public Product() {
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public BigDecimal getProduct_price() {
        return product_price;
    }

    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public String getProduct_classification() {
        return product_classification;
    }

    public void setProduct_classification(String product_classification) {
        this.product_classification = product_classification;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public List<Imgproduct> getImgproductList() {
        return imgproductList;
    }

    public void setImgproductList(List<Imgproduct> imgproductList) {
        this.imgproductList = imgproductList;
    }

    public Product(String product_name, String product_description, BigDecimal product_price, String product_classification, int product_quantity, Date date_created, int category_id, List<Imgproduct> imgproductList) {
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_classification = product_classification;
        this.product_quantity = product_quantity;
        this.date_created = date_created;
        this.category_id = category_id;
        this.imgproductList = imgproductList;
    }

    public Product(int product_id, String product_name, String product_description, BigDecimal product_price, String product_classification, int product_quantity, Date date_created, int category_id, List<Imgproduct> imgproductList) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_price = product_price;
        this.product_classification = product_classification;
        this.product_quantity = product_quantity;
        this.date_created = date_created;
        this.category_id = category_id;
        this.imgproductList = imgproductList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", product_description='" + product_description + '\'' +
                ", product_price=" + product_price +
                ", product_classification='" + product_classification + '\'' +
                ", product_quantity=" + product_quantity +
                ", date_created=" + date_created +
                ", category_id=" + category_id +
                ", imgproductList=" + imgproductList +
                '}';
    }

}
