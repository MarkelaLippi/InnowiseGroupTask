package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.interfaces.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {
    @Override
    public boolean validate(String email) {
        final Pattern pattern = Pattern.compile(".+@.+\\..+");
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
