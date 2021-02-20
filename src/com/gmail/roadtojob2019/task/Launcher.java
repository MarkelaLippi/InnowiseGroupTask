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
        final Validator phoneValidator = new PhoneValidator();
        final Validator emailValidator = new EmailValidator();
        final Creator userCreator = new CreatorImpl(printer, reader, phoneValidator, emailValidator);
        final Map<String, User> storage = new HashMap<>();
        final Saver saver = new SaverImpl(storage, printer);
        final ExtractorImpl<String, User> extractor = new ExtractorImpl<>(storage, printer);

        final User newUser = (User) userCreator.create();
        printer.print("User1 is created");
        printer.print(newUser);
        saver.save(newUser);
        printer.print("User1 is saved");

        final User extractedUser = extractor.extract(newUser.getEmail());
        printer.print("User is extracted");
        printer.print(extractedUser);

        final UserModifier modifier = new ModifierImpl(printer, reader, phoneValidator, emailValidator);
        final User modifiedUser = modifier.modify(extractedUser);
        saver.save(modifiedUser);
        printer.print(extractor.extract(modifiedUser.getEmail()));

    }
}
