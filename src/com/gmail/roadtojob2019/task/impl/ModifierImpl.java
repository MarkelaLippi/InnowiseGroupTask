package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.entity.Role;
import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.interfaces.Modifier;
import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Reader;
import com.gmail.roadtojob2019.task.interfaces.Validator;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class ModifierImpl implements Modifier<User> {
    private final Printer<String> printer;
    private final Reader<String> reader;
    private final Validator<String> phoneValidator;
    private final Validator<String> emailValidator;

    public ModifierImpl(Printer<String> printer, Reader<String> reader, Validator<String> phoneValidator, Validator<String> emailValidator) {
        this.printer = printer;
        this.reader = reader;
        this.phoneValidator = phoneValidator;
        this.emailValidator = emailValidator;
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
                    if (abortSelectionProcess()) {
                        flag = false;
                    }
                }
                case 2 -> {
                    printer.print("Enter the user's surname");
                    user.setSurname(reader.read());
                    if (abortSelectionProcess()) {
                        flag = false;
                    }
                }
                case 3 -> {
                    printer.print("Enter the user's email");
                    user.setEmail(getValidUserEmail());
                    if (abortSelectionProcess()) {
                        flag = false;
                    }
                }
                case 4 -> {
                    printer.print("Enter the user's phones (min=1, max=3)");
                    user.setPhones(getValidUserPhones());
                    if (abortSelectionProcess()) {
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
                    user.setRoles(getValidUserRoles());
                    if (abortSelectionProcess()) {
                        flag = false;
                    }
                }
            }
        } while (flag);
        return user;
    }

    private String getValidUserEmail() {
        String email;
        do {
            email = reader.read();
            if (!emailValidator.validate(email)) {
                printer.print("The email you entered has an incorrect format. Please try again.");
            }
        } while (!emailValidator.validate(email));
        return email;
    }

    private Set<String> getValidUserPhones() {
        Set<String> phones = new HashSet<>();
        String phone;
        for (int n = 1; n <= 3; n++) {
            do {
                printer.print("Enter the phone № " + n);
                phone = reader.read();
                if (!phoneValidator.validate(phone)) {
                    printer.print("The phone number you entered has an incorrect format. Please try again.");
                }
            } while (!phoneValidator.validate(phone));
            phones.add(phone);
            if (n < 3) {
                if (abortAddingProcess()) {
                    break;
                }
            }
        }
        return phones;
    }

    private Set<Role> getValidUserRoles() {
        Set<Role> roles = EnumSet.noneOf(Role.class);
        for (int n = 1; n <= 2; n++) {
            int roleNumber = 0;
            Role role = null;
            do {
                printer.print("Enter the role № " + n);
                try {
                    roleNumber = Integer.parseInt(reader.read());
                } catch (NumberFormatException e) {
                    printer.print("The data you entered is in the wrong format.");
                    continue;
                }

                try {
                    role = switch (roleNumber) {
                        case 1 -> Role.USER;
                        case 2 -> Role.CUSTOMER;
                        case 3 -> Role.ADMIN;
                        case 4 -> Role.PROVIDER;
                        case 5 -> Role.SUPER_ADMIN;
                        default -> throw new IllegalStateException();
                    };
                } catch (IllegalStateException e) {
                    printer.print("The number you entered is outside the allowed values. Enter a number from 1 to 5.");
                    continue;
                }

                if (n == 2) {
                    int checksum = 0;
                    final Role firstUserRole = roles.stream()
                            .findFirst()
                            .get();
                    checksum += firstUserRole.getLevel();
                    checksum += role.getLevel();
                    if (firstUserRole.getLevel() == role.getLevel() || checksum > 3) {
                        printer.print("The role you entered is not level-compatible with the previous one. Please try again.");
                        roleNumber = 0;
                    }
                }
            } while (roleNumber < 1 || roleNumber > Role.values().length);

            roles.add(role);

            if (role.getLevel() == 3) {
                break;
            }

            if (n == 1) {
                if (abortAddingProcess()) {
                    break;
                }
            }
        }
        return roles;
    }

    private boolean abortAddingProcess() {
        printer.print("Do you want to add more? (YES/NO)");
        printer.print("If 'YES', enter any character except 'n'");
        printer.print("If 'NO', enter 'n'");
        final String answer = reader.read();
        return answer.equals("n");
    }

    private boolean abortSelectionProcess() {
        printer.print("Do you want to change another field? (YES/NO)");
        printer.print("If 'YES', enter any character except 'n'");
        printer.print("If 'NO', enter 'n'");
        final String answer = reader.read();
        return answer.equals("n");
    }
}
