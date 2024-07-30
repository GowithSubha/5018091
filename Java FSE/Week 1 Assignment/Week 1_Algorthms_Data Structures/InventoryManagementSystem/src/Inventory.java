import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private HashMap<Integer, Product> products;

    public Inventory() {
        this.products = new HashMap<>();
    }

    // adding new product to the inventory
    public void addProduct(Product product) {
        products.put(product.productId, product);
    }

    // updating the existing product by id
    public void updateProduct(int productId, Product updatedProduct) {
        if (products.containsKey(productId)) {
            products.put(productId, updatedProduct);
        } else {
            System.out.println("Product is not exist in inventory");
        }
    }

    // deleting the product by id
    public void deleteProduct(int productId) {
        if (products.containsKey(productId)) {
            products.remove(productId);
        } else {
            System.out.println("Product is not exist in inventory");
        }
    }

    // displaying all the products in the inventory
    public void displayProducts() {
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            System.out.println(entry.getValue());
        }
    }

}