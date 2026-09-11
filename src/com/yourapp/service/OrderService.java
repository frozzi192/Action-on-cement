package com.yourapp.service;

import com.yourapp.model.Order;
import com.yourapp.parser.IOrderParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private static final double START_DISCOUNT_PERCENT = 50.0;
    private static final double DISCOUNT_STEP = 5.0;
    private static final double PRICE_PER_TON = 10.0;

    private final IOrderParser parser;

    public OrderService(IOrderParser parser) {
        this.parser = parser;
    }

    public void processOrders() throws IOException {

        Path firstFile = Path.of("src/resources/discount_day.txt");

        Path secondFile = Path.of("src/resources/discount_day_without_ext.txt");

        List<Order> orders = new ArrayList<>();

        orders.addAll(parser.parse(firstFile));

        orders.sort(Comparator.comparing(Order::getSubmissionTime));

        Map<String, Double> companyTotals = calculateCompanyTotals(orders);

        saveResults(companyTotals);
    }

    private Map<String, Double> calculateCompanyTotals(List<Order> orders) {

        Map<String, Double> companyTotals = new HashMap<>();

        double currentDiscount = START_DISCOUNT_PERCENT;

        for (Order order : orders) {

            double fullPrice = order.getWeight() * PRICE_PER_TON;

            double discountAmount = fullPrice * currentDiscount / 100.0;

            double finalPrice = fullPrice - discountAmount;

            System.out.printf("Время: %s | Компания: %s | Вес: %.2f | " + "Скидка: %.2f%% | Стоимость: %.2f%n", order.getSubmissionTime(), order.getNameCompany(), order.getWeight(), currentDiscount, finalPrice);

            companyTotals.merge(order.getNameCompany(), finalPrice, Double::sum);

            currentDiscount -= DISCOUNT_STEP;

            if (currentDiscount < 0) {
                currentDiscount = 0;
            }
        }

        return companyTotals;
    }

    private void saveResults(Map<String, Double> companyTotals) {

        for (Map.Entry<String, Double> entry : companyTotals.entrySet()) {

            System.out.println(
                    entry.getKey() + " - " +
                            String.format("%.2f", entry.getValue())
            );
        }
    }
}