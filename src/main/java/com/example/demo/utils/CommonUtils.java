package com.example.demo.utils;

import java.util.regex.Matcher;

public class CommonUtils {

    public static boolean validateEmailPattern(String emailStr) {
        Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePhoneNumPattern(String phoneNum) {
        Matcher matcher = Constants.VALID_PHONE_NUM_REGEX .matcher(phoneNum);
        return matcher.find();
    }
}
