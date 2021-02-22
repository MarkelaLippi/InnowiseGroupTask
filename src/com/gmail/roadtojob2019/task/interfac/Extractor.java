package com.gmail.roadtojob2019.task.interfac;

public interface Extractor<K, V> {
    V extract(K key);
}
