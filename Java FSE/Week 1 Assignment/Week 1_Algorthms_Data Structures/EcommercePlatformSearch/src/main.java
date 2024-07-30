import java.util.*;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Product[] products = new Product[5];
        products[0] = new Product(1, "Laptop", "Electronics");
        products[1] = new Product(2, "Mobile", "Electronics");
        products[2] = new Product(3, "T-Shirt", "Fashion");
        products[3] = new Product(4, "Shoe", "Footwear");
        products[4] = new Product(5, "Book", "Stationary");

        System.out.println("Enter the product id to search: ");
        int id = sc.nextInt();
        int index = LinearSearch.search(products, id);
        if (index != -1) {
            System.out.println("Product found at index: " + index);
        } else {
            System.out.println("Product not found");
        }

        Arrays.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getP_Id() - o2.getP_Id();
            }
        });

        index = BinarySearch.search(products, id);
        if (index != -1) {
            System.out.println("Product found at index: " + index);
        } else {
            System.out.println("Product not found");
        }
    }

}
