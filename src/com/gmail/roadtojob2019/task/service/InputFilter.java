package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.entity.Role;
import com.gmail.roadtojob2019.task.interfac.Printer;
import com.gmail.roadtojob2019.task.interfac.Reader;
import com.gmail.roadtojob2019.task.interfac.Validator;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static com.gmail.roadtojob2019.task.util.Pattern.*;

public class InputFilter {
    private final Printer<String> printer;
    private final Reader<String> reader;
    private final Validator<String, String> validator;

    public InputFilter(Printer<String> printer, Reader<String> reader, Validator<String, String> validator) {
        this.printer = printer;
        this.reader = reader;
        this.validator = validator;
    }

    public String filterInputAndGetValidUserEmail() {
        String email;
        do {
            email = reader.read();
            if (!validator.validate(email, EMAIL_PATTERN)) {
                printer.print("The email you entered has an incorrect format. Please try again.");
            }
        } while (!validator.validate(email, EMAIL_PATTERN));
        return email;
    }

    public Set<String> filterInputAndGetValidUserPhones() {
        Set<String> phones = new HashSet<>();
        String phone;
        for (int n = 1; n <= 3; n++) {
            do {
                printer.print("Enter the phone № " + n);
                phone = reader.read();
                if (!validator.validate(phone, PHONE_PATTERN)) {
                    printer.print("The phone number you entered has an incorrect format. Please try again.");
                }
            } while (!validator.validate(phone, PHONE_PATTERN));
            phones.add(phone);
            if (n < 3) {
                if (abortAddingProcess()) {
                    break;
                }
            }
        }
        return phones;
    }

    public Set<Role> filterInputAndGetValidUserRoles() {
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

    public boolean abortSelectionProcess() {
        printer.print("Do you want to change another field? (YES/NO)");
        printer.print("If 'YES', enter any character except 'n'");
        printer.print("If 'NO', enter 'n'");
        final String answer = reader.read();
        return answer.equals("n");
    }

    private boolean abortAddingProcess() {
        printer.print("Do you want to add more? (YES/NO)");
        printer.print("If 'YES', enter any character except 'n'");
        printer.print("If 'NO', enter 'n'");
        final String answer = reader.read();
        return answer.equals("n");
    }
}
