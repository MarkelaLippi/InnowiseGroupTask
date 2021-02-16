package com.gmail.roadtojob2019.task;

import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.impl.*;
import com.gmail.roadtojob2019.task.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class Launcher {
    public static void main(String[] args) throws Exception {
        final Printer printer = new PrinterImpl();
        final Reader reader = new ReaderImpl(printer);
        final Validator phoneValidator=new PhoneValidator();
        final Validator emailValidator=new EmailValidator();
        final UserCreator userCreator = new UserCreatorImpl(printer, reader, phoneValidator, emailValidator);
        final User newUser = userCreator.create();
        printer.print(newUser);
        Map<String, User> storage=new HashMap<>();
        final Saver saver = new SaverImpl(storage, printer);
        saver.save(newUser);


    }
}
