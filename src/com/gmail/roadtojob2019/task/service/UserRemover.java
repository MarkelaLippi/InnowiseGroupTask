package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.entity.User;
import com.gmail.roadtojob2019.task.interfac.Printer;
import com.gmail.roadtojob2019.task.interfac.Remover;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class UserRemover implements Remover<String> {
    private final Map<String, User> storage;
    private final Printer<String> printer;

    public UserRemover(Map<String, User> storage, Printer<String> printer) {
        this.storage = storage;
        this.printer = printer;
    }

    @Override
    public boolean remove(String key) {
        if(storage.remove(key)==null){
            return false;
        };
        final String filePath = "src/com/gmail/roadtojob2019/task/storage";
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(storage);
        } catch (IOException e) {
            printer.print("You have entered an incorrect path to the file storage.");
        }
        return true;
    }
}
