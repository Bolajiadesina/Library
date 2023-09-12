package com.berenberg.library.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import com.berenberg.library.model.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Bolaji
 * created on 27/08/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class BorrowedItemResponse {

     //use the item class as part of the parameter in the borrowed item class
        private Item item;

        //id of the user 
        @NotEmpty(message = "UserId cannot be empty")
        private String userId;

        //Name of the user that borrowed an item
        @NonNull
        private String userName;

        //the date at which the item was borrowed
         @NonNull
        private Date borrowedItemDate;
        //the due date for user to retur the item
         @NonNull
        private Date itemDueDate;
    
}
