package com.baold.expense_management.utils;

import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static final DateTimeFormatter FORMAT_DMY = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString().trim().isEmpty();
    }

    public static String safeToString(Object obj) {
        return safeToString(obj, "");
    }

    public static boolean safeEquals(Object obj1, Object obj2) {
        return (obj1 != null) && (obj2 != null) && obj1.toString().equals(obj2.toString());
    }

    public static String safeToString(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        return obj.toString();
    }

    public static String safeTrim(String str) {
        if (str != null) {
            return str.trim();
        }
        return str;
    }
}
