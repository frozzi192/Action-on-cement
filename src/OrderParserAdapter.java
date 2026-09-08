import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class OrderParserAdapter implements IOrderParser{

    private final AbstractOrderParser reader;

    public OrderParserAdapter(AbstractOrderParser reader) {
        this.reader = reader;
    }

    @Override
    public List<Order> parse(Path filePath) throws IOException {
        return reader.parse(filePath);
    }
}
