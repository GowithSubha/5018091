public class Product {
    private int productId;
    private String productName;
    private String Category;

    public Product(int productId, String productName, String Category) {
        this.productId = productId;
        this.productName = productName;
        this.Category = Category;
    }

    public int getP_Id() {
        return productId;
    }

    public void setP_Id(int productId) {
        this.productId = productId;
    }

    public String getP_Name() {
        return productName;
    }

    public void setP_Name(String productName) {
        this.productName = productName;
    }

    public String getP_Category() {
        return Category;
    }

    public void setP_Category(String Category) {
        this.Category = Category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }

}
