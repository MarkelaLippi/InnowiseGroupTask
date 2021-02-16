package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.entity.Role;
import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Reader;
import com.gmail.roadtojob2019.task.interfaces.UserCreator;
import com.gmail.roadtojob2019.task.interfaces.Validator;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class UserCreatorImpl implements UserCreator {
    private final Printer printer;
    private final Reader reader;
    private final Validator phoneValidator;
    private final Validator emailValidator;

    public UserCreatorImpl(Printer printer, Reader reader, Validator phoneValidator, Validator emailValidator) {
        this.printer = printer;
        this.reader = reader;
        this.phoneValidator = phoneValidator;
        this.emailValidator = emailValidator;
    }

    @Override
    public User create() {
        final User.Builder builder = User.builder();
        printer.print("Enter the user's name");
        builder.withName(reader.read());
        printer.print("Enter the user's surname");
        builder.withSurname(reader.read());
        printer.print("Enter the user's email");
        builder.withEmail(getValidUserEmail());
        printer.print("Enter the user's phones (min=1, max=3)");
        builder.withPhones(getValidUserPhones());
        printer.print("Enter the user's roles (min=1, max=2)");
        printer.print("Press '1' to select 'USER(l.1)'");
        printer.print("Press '2' to select 'CUSTOMER(l.1)'");
        printer.print("Press '3' to select 'ADMIN(l.2)'");
        printer.print("Press '4' to select 'PROVIDER(l.2)'");
        printer.print("Press '5' to select 'SUPER_ADMIN(l.3)'");
        Set<Role> roles = getValidUserRoles();
        builder.withRoles(roles);
        return builder.build();
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
                if (abortProcess()) break;
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
                if (abortProcess()) {
                    break;
                }
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

