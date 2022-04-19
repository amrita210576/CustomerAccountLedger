package com.example;

import Service.BorrowerServiceImpl;
import handler.InputHandler;
import handler.TextInputHandlerImpl;
import java.io.IOException;

public class Geektrust {

    public static void main(String[] args) throws IOException {
        String filePath = args[0];
        InputHandler inputHandler = new TextInputHandlerImpl(new BorrowerServiceImpl());
        inputHandler.readInput(filePath);
    }
}