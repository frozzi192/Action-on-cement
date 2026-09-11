package com.yourapp.parser;

import com.yourapp.model.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class OrderParserAdapter implements IOrderParser {

    private final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public List<Order> parse(Path filePath) throws IOException {

        List<Order> orders = new ArrayList<>();

        for (String line : Files.readAllLines(filePath)) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String delimiter;

            if (line.contains("#")) {
                delimiter = "#";
            } else {
                delimiter = "\\|";
            }

            String[] parts = line.split(delimiter);

            if (parts.length != 3) {
                System.out.println("Неверный формат строки: " + line);
                continue;
            }

            try {
                LocalDateTime time = LocalDateTime.parse(parts[0].trim(), formatter);

                String company = parts[1].trim();

                double weight = Double.parseDouble(parts[2].trim());

                orders.add(new Order(time, company, weight));

            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат даты: " + line);

            } catch (NumberFormatException e) {
                System.out.println("Ошибка в весе: " + line);
            }
        }

        return orders;
    }

    private String determineDelimiter(Path filePath) {

        if (filePath.getFileName().toString().endsWith(".txt")) {
            return "\\|";
        }

        return "#";
    }
}