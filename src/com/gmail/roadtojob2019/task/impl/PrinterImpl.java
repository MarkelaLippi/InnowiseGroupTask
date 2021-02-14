package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.Printer;

public class PrinterImpl implements Printer {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
