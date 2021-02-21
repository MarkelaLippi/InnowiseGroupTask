package com.gmail.roadtojob2019.task;

import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.service.*;
import com.gmail.roadtojob2019.task.interfaces.*;

import java.util.HashMap;
import java.util.Map;

public class Launcher {
    public static void main(String[] args) {
        final Printer<String> printer = new ConsolePrinter<>();
        final Reader<String> reader = new ConsoleReader(printer);
        final Validator<String, String> validator = new UserValidator();
        final InputFilter filter=new InputFilter(printer,reader,validator);
        final Creator<User> creator = new UserCreator(printer, reader, filter);
        final Map<String, User> storage = new HashMap<>();
        final Saver<User> saver = new UserSaver(storage, printer);
        final Extractor<String, User> extractor = new UserExtractor(storage, printer);
        final Modifier<User> modifier = new UserModifier(printer, reader, filter);
        final Remover<String> remover = new UserRemover(storage, printer);
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
                    final User newUser = creator.create();
                    saver.save(newUser);
                    printer.print("The user is successfully created.");
                }
                case 2 -> {
                    printer.print("Enter the email of the user you want to change.");
                    final String email = reader.read();
                    final User extractedUser = extractor.extract(email);
                    if (extractedUser == null) {
                        printer.print("The user with the email " + email + " was not detected in the storage.");
                        break;
                    }
                    final User modifiedUser = modifier.modify(extractedUser);
                    saver.save(modifiedUser);
                }
                case 3 -> {
                    printer.print("Enter the user's email.");
                    final String email = reader.read();
                    if (remover.remove(email)) {
                        printer.print("The user with the email " + email + " was deleted.");
                    } else {
                        printer.print("The user with the email " + email + " was not detected in the storage.");
                    }
                }
                case 4 -> {
                    printer.print("Enter the email of the user you want to display on the console.");
                    final String email = reader.read();
                    final User extractedUser = extractor.extract(email);
                    if (extractedUser == null) {
                        printer.print("The user with the email " + email + " was not detected in the storage.");
                        break;
                    }
                    printer.print(extractedUser.toString());
                }
                case 5 -> {
                    if (storage.isEmpty()) {
                        printer.print("Storage is empty. Create at least one user.");
                    } else {
                        for (User user : storage.values()) {
                            printer.print(user.toString());
                        }
                    }
                }
            }
        }
        while (true);
    }
}