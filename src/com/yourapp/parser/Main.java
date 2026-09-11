package com.yourapp.parser;

import com.yourapp.parser.OrderParserAdapter;
import com.yourapp.service.OrderService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        new OrderService(new OrderParserAdapter()).processOrders();
    }
}