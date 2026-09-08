import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static java.util.logging.Level.parse;

public class Main {
    private static final double START_DISCOUNT_PERCENT = 50.0;
    private static final double DISCOUNT_STEP = 5.0;

    private static final double PRICE_PER_TON = 10.0;

    static void main(String[] args) throws IOException {
        IOrderParser pipeParser = new OrderParserAdapter(new PipeOrderParser());

        IOrderParser hashParser = new OrderParserAdapter(new HashOrderReader());

        Path pipeFile = Path.of("src/discount_day.txt");
        Path hashFile = Path.of("src/discount_day_without_ext.txt");

        List<Order> pipeOrders = pipeParser.parse(pipeFile);
        List<Order> hashOrders = hashParser.parse(hashFile);

        List<Order> allOrders = new ArrayList<>();

        allOrders.addAll(pipeOrders);
        allOrders.addAll(hashOrders);

        allOrders.sort(Comparator.comparing(Order::getSubmissionTime));

        Map<String, Double> companyTotals = calculateCompanyTotals(allOrders);

        System.out.println("Итоговые суммы по компаниям:");

        for (Map.Entry<String, Double> entry : companyTotals.entrySet()) {
            System.out.printf("%s - %.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private static Map<String, Double> calculateCompanyTotals(List<Order> orders) {
        Map<String, Double> companyTotals = new HashMap<>();

        double currentDiscount = START_DISCOUNT_PERCENT;

        for (Order order : orders) {

            double fullPrice = order.getWeight() * PRICE_PER_TON;
            double discountAmount = fullPrice * currentDiscount / 100.0;
            double finalPrice = fullPrice - discountAmount;

            companyTotals.merge(
                    order.getNameCompany(),
                    finalPrice,
                    Double::sum
            );

            System.out.printf("Время: %s | Компания: %s | Вес: %.2f | " + "Скидка: %.2f%% | Стоимость: %.2f%n", order.getSubmissionTime(), order.getNameCompany(), order.getWeight(), currentDiscount, finalPrice);

            currentDiscount -= DISCOUNT_STEP;

            if (currentDiscount < 0) {
                currentDiscount = 0;
            }
        }

        return companyTotals;
    }
}
