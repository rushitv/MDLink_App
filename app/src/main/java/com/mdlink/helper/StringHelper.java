package com.mdlink.helper;

import android.util.Patterns;

import static com.mdlink.util.Constants.INVALID;

public class StringHelper {

    /**
     * Checks if the string is null or empty
     *
     * @param string String to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmptyOrNull(String string) {
        return null == string || string.isEmpty();
    }

    /**
     * Checks if a string contains valid length of characters
     */
    public static boolean isValidLength(String string, int minLength, int maxLength) {
        if (isEmptyOrNull(string)) {
            return false;
        }
        boolean minLengthSatisfied = true, maxLengthSatisfied = true;

        if (INVALID != minLength) {
            if (string.length() < minLength) {
                minLengthSatisfied = false;
            }
        }

        if (INVALID != maxLength) {
            if (string.length() > maxLength) {
                maxLengthSatisfied = false;
            }
        }

        return minLengthSatisfied && maxLengthSatisfied;
    }

    /**
     * Checks if an email id is in valid format
     *
     * @param email Email id to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean equals(String s1, String s2) {
        if (isEmptyOrNull(s1) || isEmptyOrNull(s2)) {
            return false;
        }

        return s1.equals(s2);
    }

    public static String GetTwoCharsOfFullName(String fullName) {

        if (isEmptyOrNull(fullName)) {
            return "";
        } else {
            String[] SplitedName = fullName.split("\\s+");
            String fChar, lChar;
            if(SplitedName.length == 0){
                return "";
            }else if(SplitedName.length == 1){
                fChar = String.valueOf(SplitedName[0].charAt(0));
                return fChar.charAt(0) + "" + fChar.charAt(0);
            }else {
                fChar = SplitedName[0];
                lChar = SplitedName[1];
                return fChar.charAt(0) + "" + lChar.charAt(0);
            }
        }

    }
}
