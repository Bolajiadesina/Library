package com.berenberg.library.Utils;

import java.util.Date;


import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CalculateDate {

    private CalculateDate() {}

    @Synchronized
    public static Date calculateReturnDate(Date borrowDate){

        // Implement your logic to calculate the return date (e.g., +7 days)
        return new Date(borrowDate.getTime() + 7 * 24 * 60 * 60 * 1000);
        
    }

    @Synchronized
    public static Date calculateNullDate(Date borrowDate){
        log.info("now===========" + borrowDate);
        // Implement your logic to calculate the return date (e.g., +7 days)
        return new Date(borrowDate.getTime() + 0 * 24 * 60 * 60 * 1000);
        
    }
    
}
