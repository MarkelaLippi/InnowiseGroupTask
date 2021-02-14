package com.gmail.roadtojob2019.task.impl;

import com.gmail.roadtojob2019.task.interfaces.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements Validator {
    @Override
    public boolean validate(String phone) {
        final Pattern pattern = Pattern.compile("375[0-9]{9}");
        final Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
