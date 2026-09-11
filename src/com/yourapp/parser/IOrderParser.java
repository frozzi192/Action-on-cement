package com.yourapp.parser;

import com.yourapp.model.Order;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IOrderParser {
    List<Order> parse(Path filePath) throws IOException;

}
