public class BubbleSort {
    public static void bubbleSort(Order[] orders) {
        int n = orders.length;
        Order temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (orders[j - 1].getTotal_Price() > orders[j].getTotal_Price()) {
                    temp = orders[j - 1];
                    orders[j - 1] = orders[j];
                    orders[j] = temp;
                }
            }
        }
    }

}
