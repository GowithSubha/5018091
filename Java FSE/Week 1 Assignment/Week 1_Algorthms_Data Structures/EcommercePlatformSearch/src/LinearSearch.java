public class LinearSearch {
    public static int search(Product[] products, int id) {
        for (int i = 0; i < products.length; i++) {
            if (products[i].getP_Id() == id) {
                return i;
            }
        }
        return -1;
    }

}
