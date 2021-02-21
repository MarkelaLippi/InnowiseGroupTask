package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.entity.Role;
import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.interfaces.Creator;
import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Reader;

import java.util.Set;

public class UserCreator implements Creator<User> {
    private final Printer<String> printer;
    private final Reader<String> reader;
    private final InputFilter filter;

    public UserCreator(Printer<String> printer, Reader<String> reader, InputFilter filter) {
        this.printer = printer;
        this.reader = reader;
        this.filter = filter;
    }

    @Override
    public User create() {
        final User.Builder builder = User.builder();
        printer.print("Enter the user's name");
        builder.withName(reader.read());
        printer.print("Enter the user's surname");
        builder.withSurname(reader.read());
        printer.print("Enter the user's email");
        builder.withEmail(filter.filterInputAndGetValidUserEmail());
        printer.print("Enter the user's phones (min=1, max=3)");
        builder.withPhones(filter.filterInputAndGetValidUserPhones());
        printer.print("Enter the user's roles (min=1, max=2)");
        printer.print("Press '1' to select 'USER(l.1)'");
        printer.print("Press '2' to select 'CUSTOMER(l.1)'");
        printer.print("Press '3' to select 'ADMIN(l.2)'");
        printer.print("Press '4' to select 'PROVIDER(l.2)'");
        printer.print("Press '5' to select 'SUPER_ADMIN(l.3)'");
        Set<Role> roles = filter.filterInputAndGetValidUserRoles();
        builder.withRoles(roles);
        return builder.build();
    }
}

