package com.gmail.roadtojob2019.task.interfaces;

public interface Validator<T, P> {
    boolean validate(T object, P pattern);
}
