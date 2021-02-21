package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Reader;

import java.util.Scanner;

public class ConsoleReader implements Reader<String> {
    private final Printer<String> printer;

    public ConsoleReader(Printer<String> printer) {
        this.printer = printer;
    }

    @Override
    public String read() {
        final Scanner scanner = new Scanner(System.in);
        String result;
        do {
            result = scanner.nextLine();
            if (result.equals("")) {
                printer.print("You didn't enter anything. Try again");
            }
        } while (result.equals(""));
        return result;
    }
}
