package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.Reader;

import java.util.Scanner;

public class ReaderImpl implements Reader {
    @Override
    public String read() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
