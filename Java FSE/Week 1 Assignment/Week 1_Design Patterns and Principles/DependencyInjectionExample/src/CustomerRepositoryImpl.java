import java.util.Map;
import java.util.HashMap;

public class CustomerRepositoryImpl implements CustomerRepository {
    private Map<Integer, Customer> customers = new HashMap<>();

    public CustomerRepositoryImpl() {
        customers.put(1, new Customer(1, "Aniket"));
        customers.put(2, new Customer(2, "Aritra"));
    }

    @Override
    public Customer findCustomerById(int id) {
        return customers.get(id);
    }
}