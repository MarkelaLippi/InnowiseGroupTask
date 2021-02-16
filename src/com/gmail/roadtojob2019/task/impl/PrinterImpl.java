package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.interfaces.Printer;

public class PrinterImpl implements Printer {
    @Override
    public void print(Object message) {
        System.out.println(message);
    }
}
