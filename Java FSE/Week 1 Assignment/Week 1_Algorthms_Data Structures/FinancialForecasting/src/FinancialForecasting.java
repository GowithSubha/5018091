import java.util.HashMap;
import java.util.Map;

public class FinancialForecasting {
    private static Map<Integer, Double> calculate = new HashMap<>();

    public static double calculateFutures(double presentValue, double rate, int years) {
        if (calculate.containsKey(years)) {
            return calculate.get(years);
        }

        if (years == 0) {
            return presentValue;
        } else {
            double futureValue = presentValue * (1 + rate);
            calculate.put(years, futureValue);
            return calculateFutures(futureValue, rate, years - 1);
        }
    }

    public static void main(String[] args) {
        double presentValue = 2000;
        double rate = 0.1;
        int years = 5;
        System.out.println("Future value of present value " + presentValue + " after " + years + " years is "
                + calculateFutures(presentValue, rate, years));
    }

}
