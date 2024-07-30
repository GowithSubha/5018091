public class Order {
    private int orderID;
    private String customerName;
    private double totalPrice;

    public Order(int orderID, String customerName, double totalPrice) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
    }

    public int getO_ID() {
        return orderID;
    }

    public String getCust_Name() {
        return customerName;
    }

    public double getTotal_Price() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", customerName='" + customerName + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
