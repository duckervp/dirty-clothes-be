package com.dirty.shop.utils;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class RegexUtils {

    public static final Pattern MERCHANT_CODE_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3}$");

    public static final Pattern BRAND_CODE_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    public static final Pattern ALPHA_NUMERIC_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");

    public static final Pattern AFFILIATE_CODE_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,50}$");

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern EMAIL_PATTERN_V2 = Pattern.compile("^[a-zA-Z0-9+_.\\-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]+$");

    public static final Pattern ONLY_NUMBER_PATTERN = Pattern.compile("^[0-9]*$");

    public static final Pattern RECORD_ID_PATTERN = Pattern.compile("^[A-Za-z]*(\\d{12,})[A-Za-z]*$");

    public static final Pattern BETTING_PLAYER_ID_PATTERN = Pattern.compile("MRMI(\\d+)PI");

    public static final Pattern ADJUSTMENT_AMOUNT_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

}
