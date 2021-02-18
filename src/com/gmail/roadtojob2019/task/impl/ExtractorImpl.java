package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.interfaces.Extractor;
import com.gmail.roadtojob2019.task.interfaces.Printer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

public class ExtractorImpl<K, V> implements Extractor<K, V> {
    private Map<K, V> storage;
    private final Printer printer;

    public ExtractorImpl(Map<K, V> storage, Printer printer) {
        this.storage = storage;
        this.printer = printer;
    }

    @Override
    public V extract(K key) {
        final String filePath = "src/com/gmail/roadtojob2019/task/storage";
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            storage = (Map<K, V>) objectInputStream.readObject();
        } catch (IOException e) {
            printer.print("There is an incorrect path to the file storage. Extracting the object failed.");
        } catch (ClassNotFoundException e) {
            printer.print("An object of this type was not found in the storage. Extracting the object failed.");
        }
        return storage.get(key);
    }
}