package springboot.springboot.relearnConectCRUD.entity;

import java.util.List;

public class Category extends Entity<Integer> {
    private int category_id;
    private String category_name;
    private List<Product> productList;

    public Category(int category_id, String category_name, List<Product> productList) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.productList = productList;
    }

    public Category(String category_name, List<Product> productList) {
        this.category_name = category_name;
        this.productList = productList;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", productList=" + productList +
                '}';
    }
}
