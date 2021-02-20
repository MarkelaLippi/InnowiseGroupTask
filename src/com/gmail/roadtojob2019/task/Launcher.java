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
        final Saver userSaver = new SaverImpl(storage, printer);
        final ExtractorImpl<String, User> userExtractor = new ExtractorImpl<>(storage, printer);
        final Modifier userModifier = new ModifierImpl(printer, reader, phoneValidator, emailValidator);
        final Remover<String> userRemover = new RemoverImpl<>(storage, printer);
        boolean flag = true;
        int menuItem = 0;
        do {
            printer.print("");
            printer.print("Welcome to the application that allows you to create, modify, delete users,\n" +
                    "as well as save information about them in a file and output it to the console.");
            printer.print("");
            printer.print("Select a menu item, please.");
            printer.print("Press '1' to create user");
            printer.print("Press '2' to modify user");
            printer.print("Press '3' to delete user'");
            printer.print("Press '4' to display information about the user on the console");
            printer.print("Press '5' to display information about all users on the console");
            printer.print("Enter 'exit' to quit the application");
            final String inputResult = reader.read();
            if (inputResult.equals("exit")) {
                break;
            }

            try {
                menuItem = Integer.parseInt(inputResult);
            } catch (NumberFormatException e) {
                printer.print("The data you entered is in the wrong format. Try again.");
            }

            switch (menuItem) {
                case 1 -> {
                    final User newUser = (User) userCreator.create();
                    userSaver.save(newUser);
                }
                case 2 -> {
                    printer.print("Enter the email of the user you want to change");
                    final String email = reader.read();
                    final User extractedUser = userExtractor.extract(email);
                    if (extractedUser == null) {
                        printer.print("The user with the email " + email + " was not detected in the storage");
                        break;
                    }
                    final User modifiedUser = userModifier.modify(extractedUser);
                    userSaver.save(modifiedUser);
                }
                case 3 -> {
                    printer.print("Enter the user's email");
                    final String email = reader.read();
                    if (userRemover.remove(email)) {
                        printer.print("The user with the email " + email + " was deleted");
                    } else {
                        printer.print("The user with the email " + email + " was not detected in the storage");
                    }
                    ;
                }
                case 4 -> {
                    printer.print("Enter the email of the user you want to display on the console");
                    final String email = reader.read();
                    final User extractedUser = userExtractor.extract(email);
                    if (extractedUser == null) {
                        printer.print("The user with the email " + email + " was not detected in the storage");
                        break;
                    }
                    printer.print(extractedUser);
                }
                case 5 -> {
                    if (storage.isEmpty()) {
                        printer.print("Storage is empty. Create at least one user");
                    } else {
                        printer.print(storage);
                    }
                }
            }
        }
        while (flag);
    }
}