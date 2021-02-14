package com.gmail.roadtojob2019.task;

import com.gmail.roadtojob2019.task.impl.*;
import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Reader;
import com.gmail.roadtojob2019.task.interfaces.UserCreator;
import com.gmail.roadtojob2019.task.interfaces.Validator;

public class Launcher {
    public static void main(String[] args) {
        final Printer printer = new PrinterImpl();
        final Reader reader = new ReaderImpl(printer);
        final Validator phoneValidator=new PhoneValidator();
        final Validator emailValidator=new EmailValidator();
        final UserCreator userCreator = new UserCreatorImpl(printer, reader, phoneValidator, emailValidator);
        userCreator.create();


    }
}
