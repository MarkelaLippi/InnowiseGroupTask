package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.interfaces.Printer;

public class PrinterImpl<T> implements Printer<T> {
    @Override
    public void print(T message) {
        System.out.println(message);
    }
}
