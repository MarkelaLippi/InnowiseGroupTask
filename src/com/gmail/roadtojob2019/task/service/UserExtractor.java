package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.interfaces.Extractor;
import com.gmail.roadtojob2019.task.interfaces.Printer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class UserExtractor implements Extractor<String, User> {
    private Map<String, User> storage;
    private final Printer<String> printer;

    public UserExtractor(Map<String, User> storage, Printer<String> printer) {
        this.storage = storage;
        this.printer = printer;
    }

    @Override
    public User extract(String key) {
        final String filePath = "src/com/gmail/roadtojob2019/task/storage";
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            storage = (Map<String, User>) objectInputStream.readObject();
        } catch (IOException e) {
            printer.print("There is an incorrect path to the file storage. Extracting the object failed.");
        } catch (ClassNotFoundException e) {
            printer.print("An object of this type was not found in the storage. Extracting the object failed.");
        }
        return storage.get(key);
    }
}