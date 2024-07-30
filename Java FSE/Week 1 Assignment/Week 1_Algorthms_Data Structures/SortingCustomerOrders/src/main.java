public class main {
    public static void main(String[] args) {
        Order[] orders = new Order[5];
        orders[0] = new Order(1, "Subha", 1000);
        orders[1] = new Order(2, "Doyel", 500);
        orders[2] = new Order(3, "Aniket", 1500);
        orders[3] = new Order(4, "Khushi", 2000);
        orders[4] = new Order(5, "Lakshya", 2500);
        System.out.println("Before sorting");
        for (Order order : orders) {
            System.out.println(order);
        }
        QuickSort.quickSort(orders, 0, orders.length - 1);
        System.out.println("After Quick sorting");
        for (Order order : orders) {
            System.out.println(order);
        }
        BubbleSort.bubbleSort(orders);
        System.out.println("After Bubble sorting");
        for (Order order : orders) {
            System.out.println(order);
        }
    }

}
