package com.example;

import handler.InputHandler;
import java.io.IOException;

public class Main {
    static InputHandler inputHandler;

    public Main(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Hello world!");
        inputHandler.readInput(args[0]);
    }
}