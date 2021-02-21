package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.interfaces.Printer;
import com.gmail.roadtojob2019.task.interfaces.Remover;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class RemoverImpl<K,V> implements Remover<K> {
    private final Map<K,V> storage;
    private final Printer<String> printer;

    public RemoverImpl(Map<K, V> storage, Printer<String> printer) {
        this.storage = storage;
        this.printer = printer;
    }

    @Override
    public boolean remove(K key) {
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
