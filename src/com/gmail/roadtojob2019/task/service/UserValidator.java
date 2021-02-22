package com.gmail.roadtojob2019.task.service;

import com.gmail.roadtojob2019.task.interfac.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator implements Validator<String, String> {
    @Override
    public boolean validate(String object, String template) {
        final Pattern pattern = Pattern.compile(template);
        final Matcher matcher = pattern.matcher(object);
        return matcher.matches();
    }
}

