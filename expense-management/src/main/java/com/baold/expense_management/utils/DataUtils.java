package com.baold.expense_management.utils;

public class DataUtils {

    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || obj.toString().trim().isEmpty();
    }
}
