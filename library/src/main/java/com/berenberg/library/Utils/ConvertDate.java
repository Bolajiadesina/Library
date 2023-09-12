package com.berenberg.library.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.Synchronized;

public class ConvertDate {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConvertDate.class);
   
    @Synchronized
    public static Date convertDates(String dateToConvert) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy",
                    Locale.ENGLISH);
            logger.info("dateToConvert===========" + dateToConvert);
            Date parsedDate = sdf.parse(dateToConvert);
            SimpleDateFormat print = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
            System.out.println(print.format(parsedDate));

        } catch (ParseException exp) {
            exp.printStackTrace();
        }
        return date;
    }


    @Synchronized
    public static void compareDate(String old, String newdate ){
            boolean result= (convertDates(old).before(convertDates(newdate)));
            System.out.println((result));
    }

    // public static void main(String[] args) {
    //     Date now= new Date();
    //      compareDate(now.toString(),"Fri Sep 20 00:00:00 BST 2013" );
    //     //compareDate("Fri Sep 20 00:00:00 BST 2013","Mon Aug 28 08:19:15 BST 2023" );
    // }

}
