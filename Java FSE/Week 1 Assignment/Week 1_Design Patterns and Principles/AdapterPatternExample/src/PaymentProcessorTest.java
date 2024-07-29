public class PaymentProcessorTest {
    public static void main(String[] args) {

        PayPalAdapter payPalAdapter = new PayPalAdapter(new PayPalGateway());
        StripeAdapter stripeAdapter = new StripeAdapter(new StripeGateway());
        BankTransferAdapter bankTransferAdapter = new BankTransferAdapter(new BankTransferGateway());

        payPalAdapter.processPayment("PayPal", 253.0);
        stripeAdapter.processPayment("Stripe", 200.75);
        bankTransferAdapter.processPayment("Bank Transfer", 480.50);
    }
}