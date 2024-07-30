import java.util.ArrayList;
import java.util.Scanner;

public class main {
    private static Scanner sc = new Scanner(System.in);
    private static Inventory inventory = new Inventory();

    public static void main(String[] args) {

        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Display Products");
            System.out.println("5. Exit");

            System.out.println("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter product id: ");
                    int p_Id = sc.nextInt();
                    System.out.println("Enter product name: ");
                    String p_Name = sc.next();
                    System.out.println("Enter product quantity: ");
                    int quantity = sc.nextInt();
                    System.out.println("Enter product price: ");
                    double price = sc.nextDouble();

                    Product product = new Product(p_Id, p_Name, quantity, price);
                    inventory.addProduct(product);
                    break;
                case 2:
                    System.out.println("Enter product id: ");
                    int updateP_Id = sc.nextInt();
                    System.out.println("Enter updated product name: ");
                    String updateP_Name = sc.next();
                    System.out.println("Enter updated product quantity: ");
                    int updateQuantity = sc.nextInt();
                    System.out.println("Enter updated product price: ");
                    double updatePrice = sc.nextDouble();

                    Product updatedProduct = new Product(updateP_Id, updateP_Name, updateQuantity, updatePrice);
                    inventory.updateProduct(updateP_Id, updatedProduct);
                    break;
                case 3:
                    System.out.println("Enter product id: ");
                    int deleteP_Id = sc.nextInt();
                    inventory.deleteProduct(deleteP_Id);
                    break;
                case 4:
                    inventory.displayProducts();
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }

        // Product product1 = new Product(1, "Product 1", 10, 9.99);
        // Product product2 = new Product(2, "Product 2", 20, 19.99);

        // inventory.addProduct(product1);
        // inventory.addProduct(product2);

        // Product updatedProduct1 = new Product(1, "Updated Product 1", 15, 10.99);
        // inventory.updateProduct(1, updatedProduct1);

        // inventory.deleteProduct(2);
    }
}