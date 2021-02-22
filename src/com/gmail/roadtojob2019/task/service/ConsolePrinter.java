package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.interfac.Printer;

public class ConsolePrinter<T> implements Printer<T> {
    @Override
    public void print(T message) {
        System.out.println(message);
    }
}
