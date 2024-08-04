package com.dirty.shop.utils;

import lombok.experimental.UtilityClass;

import java.text.Normalizer;
import java.util.regex.Pattern;

@UtilityClass
public class BusinessUtils {
    private static String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String genSlug(String text) {
        text = text.replaceAll("[^a-zA-Z0-9\\-]", "");
        return String.join("-", deAccent(text).toLowerCase().split("\\s+"));
    }
}
