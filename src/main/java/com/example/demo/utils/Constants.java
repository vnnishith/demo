package com.example.demo.utils;

import java.util.regex.Pattern;

public class Constants {

    public static final int PASSWORD_MISMATCH_EXCEPTION = 1001;
    public static final int USER_INVALID_EMAIL_ID_EXCEPTION = 1002;
    public static final int USER_EMAIL_ID_NOT_UNIQUE_EXCEPTION = 1003;
    public static final int USER_INVALID_PHONE_NUM_EXCEPTION = 1004;
    public static final int USER_PHONE_NUM_NOT_UNIQUE_EXCEPTION = 1005;
    public static final int USER_NAME_NOT_UNIQUE_EXCEPTION = 1006;
    public static final int USER_MINPHONE_COUNT_EXCEPTION = 1007;
    public static final int USER_PREFERRED_PHONE_DELETION_EXCEPTION = 1008;
    public static final int USER_PREFERRED_PHONE_NOT_LINKED_EXCEPTION = 1009;
    public static final int INVALID_PAGENUMBER_EXCEPTION = 1010;
    public static final int MISSING_PHONE_DETAILS_EXCEPTION = 1011;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[a-z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-z0-9.-]+$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_NUM_REGEX = Pattern.compile("^[+0-9-]+$");
    public static final int PER_PAGE_LIMIT = 5;

}
