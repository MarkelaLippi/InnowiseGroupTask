package com.gmail.roadtojob2019.task;

import com.gmail.roadtojob2019.task.impl.PrinterImpl;
import com.gmail.roadtojob2019.task.impl.ReaderImpl;
import com.gmail.roadtojob2019.task.impl.UserCreatorImpl;

public class Launcher {
    public static void main(String[] args) {
        final Printer printer = new PrinterImpl();
        final Reader reader = new ReaderImpl();
        final UserCreator userCreator = new UserCreatorImpl(printer, reader);
        userCreator.create();


    }
}
