package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Saver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class UserSaver implements Saver<User> {
    private final Map<String, User> storage;
    private final Printer<String> printer;

    public UserSaver(Map<String, User> storage, Printer<String> printer) {
        this.storage = storage;
        this.printer = printer;
    }

    @Override
    public boolean save(User user){
        User savedUser = storage.put(user.getEmail(), user);
        final String filePath = "src/com/gmail/roadtojob2019/task/storage";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(storage);
        } catch (IOException e) {
            printer.print("You have entered an incorrect path to the file storage. Saving the object failed.");
            savedUser=null;
        }
        return (savedUser != null);
    }
}
