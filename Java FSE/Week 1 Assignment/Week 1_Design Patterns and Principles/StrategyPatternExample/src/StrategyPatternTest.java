public class StrategyPatternTest {
    public static void main(String[] args) {
        PaymentContext paymentContext = new PaymentContext(null);

        paymentContext.setPaymentStrategy(new CreditCardPayment("6050-4700-0012-4628", "12/2035"));
        paymentContext.executePayment(1234.0);

        paymentContext.setPaymentStrategy(new PayPalPayment("subhasaha202@gmail.com"));
        paymentContext.executePayment(2695.0);
    }
}