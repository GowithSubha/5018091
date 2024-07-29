public class PayPalAdapter implements PaymentProcessor {
    private PayPalGateway payPalGateway;

    public PayPalAdapter(PayPalGateway payPalGateway) {
        this.payPalGateway = payPalGateway;
    }

    @Override
    public void processPayment(String paymentMethod, double amount) {
        payPalGateway.payWithPayPal(paymentMethod, amount);
    }
}
