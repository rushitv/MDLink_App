package com.mdlink.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ValidationsUtil {

    public static boolean isPasswordValid(String nPwd, String cPwd){
        return (nPwd == cPwd);
    }
    public static String getPaddedNumber(int number) {
        return String.format("%02d", number);
    }

    public static String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
