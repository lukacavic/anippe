package com.velebit.anippe.shared.utilities;

public class ArrayUtility {
    public static boolean isValidIndex(Object[] arr, int index) {
        return index >= 0 && index < arr.length;
    }
}
