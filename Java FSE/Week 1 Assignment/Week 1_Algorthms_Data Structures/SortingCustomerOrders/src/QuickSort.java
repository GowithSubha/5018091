public class QuickSort {
    public static void quickSort(Order[] orders, int low, int high) {
        if (orders == null || orders.length == 0) {
            return;
        }
        if (low >= high) {
            return;
        }
        int middle = low + (high - low) / 2;
        double pivot = orders[middle].getTotal_Price();
        int i = low, j = high;
        while (i <= j) {
            while (orders[i].getTotal_Price() < pivot) {
                i++;
            }
            while (orders[j].getTotal_Price() > pivot) {
                j--;
            }
            if (i <= j) {
                Order temp = orders[i];
                orders[i] = orders[j];
                orders[j] = temp;
                i++;
                j--;
            }
        }
        if (low < j) {
            quickSort(orders, low, j);
        }
        if (high > i) {
            quickSort(orders, i, high);
        }
    }
}
