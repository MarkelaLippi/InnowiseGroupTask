package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.Printer;
import com.gmail.roadtojob2019.task.Reader;
import com.gmail.roadtojob2019.task.UserCreator;
import com.gmail.roadtojob2019.task.entity.Role;
import com.gmail.roadtojob2019.task.entity.User;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class UserCreatorImpl implements UserCreator {
    private final Printer printer;
    private final Reader reader;

    public UserCreatorImpl(Printer printer, Reader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    @Override
    public User create() {
        final User.Builder builder = User.builder();

        printer.print("Enter the user's name");
        builder.withName(reader.read());

        printer.print("Enter the user's surname");
        builder.withSurname(reader.read());

        printer.print("Enter the user's email");
        builder.withEmail(reader.read());

        printer.print("Enter the user's phones (min=1, max=3)");
        builder.withPhones(getUserPhones());

        printer.print("Enter the user's roles (min=1, max=2)");
        printer.print("Press '1' to select 'USER(l.1)'");
        printer.print("Press '2' to select 'CUSTOMER(l.1)'");
        printer.print("Press '3' to select 'ADMIN(l.2)'");
        printer.print("Press '4' to select 'PROVIDER(l.2)'");
        printer.print("Press '5' to select 'SUPER_ADMIN(l.3)'");
        Set<Role> roles = getUserRoles();
        builder.withRoles(roles);


        System.out.println(builder.build());


        return builder.build();
    }

    private Set<String> getUserPhones() {
        Set<String> phones = new HashSet<>();
        for (int n = 1; n <= 3; n++) {
            printer.print("Enter the phone № " + n);
            final String phone = reader.read();
            phones.add(phone);
            if (n < 3) {
                if (abortProcess()) break;
            }
        }
        return phones;
    }

    private Set<Role> getUserRoles() {
        Set<Role> roles = EnumSet.noneOf(Role.class);
        for (int n = 1; n <= 2; n++) {
            printer.print("Enter the role № " + n);
            int roleNumber = 0;
            do {
                try {
                    roleNumber = Integer.parseInt(reader.read());
                } catch (NumberFormatException e) {
                    printer.print("The data you entered is in the wrong format.");
                }
                switch (roleNumber) {
                    case 1 -> roles.add(Role.USER);
                    case 2 -> roles.add(Role.CUSTOMER);
                    case 3 -> roles.add(Role.ADMIN);
                    case 4 -> roles.add(Role.PROVIDER);
                    case 5 -> roles.add(Role.SUPER_ADMIN);
                    default -> {
                        printer.print("Enter a number from 1 to 5.");
                    }
                }
            } while (roleNumber < 1 || roleNumber > Role.values().length);

            if (n < 2) {
                if (abortProcess()) break;
            }
        }
        return roles;
    }

    private boolean abortProcess() {
        printer.print("Do you want to add more? (YES/NO)");
        printer.print("If 'YES', enter any character except 'n'");
        printer.print("If 'NO', enter 'n'");
        final String answer = reader.read();
        if (answer.equals("n")) {
            return true;
        }
        return false;
    }
}

