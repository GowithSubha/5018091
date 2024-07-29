public class BankTransferAdapter implements PaymentProcessor {
    private BankTransferGateway bankTransferGateway;

    public BankTransferAdapter(BankTransferGateway bankTransferGateway) {
        this.bankTransferGateway = bankTransferGateway;
    }

    @Override
    public void processPayment(String paymentMethod, double amount) {
        bankTransferGateway.transferWithBank(paymentMethod, amount);
    }
}