package springboot.springboot.relearnConectCRUD.entity;

public class Imgproduct extends Entity<Integer>{
    private int imgproduct_id;
    private int product_id;
    private String image;

    public Imgproduct(int imgproduct_id, int product_id, String image) {
        this.imgproduct_id = imgproduct_id;
        this.product_id = product_id;
        this.image = image;
    }

    public Imgproduct(int product_id, String image) {
        this.product_id = product_id;
        this.image = image;
    }

    public int getImgproduct_id() {
        return imgproduct_id;
    }

    public void setImgproduct_id(int imgproduct_id) {
        this.imgproduct_id = imgproduct_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Imgproduct{" +
                "imgproduct_id=" + imgproduct_id +
                ", product_id=" + product_id +
                ", image='" + image + '\'' +
                '}';
    }
}
