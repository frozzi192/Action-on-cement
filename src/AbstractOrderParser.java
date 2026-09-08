import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractOrderParser implements IOrderParser {

    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public List<Order> parse(Path filePath) throws IOException {
        List<Order> orders = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(getDelimiter());
            if (parts.length != 3) {
                System.out.println("Неверный формат строки: " + line);
                continue;
            }
            String timeStr = parts[0].trim();
            String company = parts[1].trim();
            String weightStr = parts[2].trim();
            try {
                LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
                double weight = Double.parseDouble(weightStr);
                Order order = new Order(time, company, weight);
                orders.add(order);
            }catch (DateTimeParseException e) {
                System.out.println("Выбран не верный формат даты");
            }catch (NumberFormatException e) {
                System.out.println("Ошибка в весе");
            }
        }
        return orders;
    }
    protected abstract String getDelimiter();

}