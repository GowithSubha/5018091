public class StripeGateway {
    public void chargeWithStripe(String paymentMethod, double amount) {
        System.out.println("Stripe payment: " + paymentMethod + ", Amount: " + amount);
    }
}
