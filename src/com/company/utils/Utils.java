package com.company.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

    public static Date convertirLocalDate2Date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertiDate2LocalDate (Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
