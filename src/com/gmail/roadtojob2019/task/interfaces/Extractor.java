package com.gmail.roadtojob2019.task.interfaces;

public interface Extractor<K, V> {
    V extract(K key);
}
