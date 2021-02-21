package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.interfaces.Modifier;
import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Reader;

public class UserModifier implements Modifier<User> {
    private final Printer<String> printer;
    private final Reader<String> reader;
    private final InputFilter filter;

    public UserModifier(Printer<String> printer, Reader<String> reader, InputFilter filter) {
        this.printer = printer;
        this.reader = reader;
        this.filter = filter;
    }

    @Override
    public User modify(User user) {
        int field = 0;
        boolean flag = true;
        do {
            printer.print("Select the field you want to change.");
            printer.print("Press '1' to select 'name'");
            printer.print("Press '2' to select 'surname'");
            printer.print("Press '3' to select 'email'");
            printer.print("Press '4' to select 'phones'");
            printer.print("Press '5' to select 'roles'");
            try {
                field = Integer.parseInt(reader.read());
            } catch (NumberFormatException e) {
                printer.print("The data you entered is in the wrong format.");
                continue;
            }

            if (field < 1 || field > 5) {
                printer.print("The number you entered is outside the allowed values. Enter a number from 1 to 5.");
                continue;
            }

            switch (field) {
                case 1 -> {
                    printer.print("Enter the user's name");
                    user.setName(reader.read());
                    if (filter.abortSelectionProcess()) {
                        flag = false;
                    }
                }
                case 2 -> {
                    printer.print("Enter the user's surname");
                    user.setSurname(reader.read());
                    if (filter.abortSelectionProcess()) {
                        flag = false;
                    }
                }
                case 3 -> {
                    printer.print("Enter the user's email");
                    user.setEmail(filter.filterInputAndGetValidUserEmail());
                    if (filter.abortSelectionProcess()) {
                        flag = false;
                    }
                }
                case 4 -> {
                    printer.print("Enter the user's phones (min=1, max=3)");
                    user.setPhones(filter.filterInputAndGetValidUserPhones());
                    if (filter.abortSelectionProcess()) {
                        flag = false;
                    }
                }
                case 5 -> {
                    printer.print("Enter the user's roles (min=1, max=2)");
                    printer.print("Press '1' to select 'USER(l.1)'");
                    printer.print("Press '2' to select 'CUSTOMER(l.1)'");
                    printer.print("Press '3' to select 'ADMIN(l.2)'");
                    printer.print("Press '4' to select 'PROVIDER(l.2)'");
                    printer.print("Press '5' to select 'SUPER_ADMIN(l.3)'");
                    user.setRoles(filter.filterInputAndGetValidUserRoles());
                    if (filter.abortSelectionProcess()) {
                        flag = false;
                    }
                }
            }
        } while (flag);
        return user;
    }
}
