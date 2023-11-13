package com.berenberg.library.dto;


import com.berenberg.library.model.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
/**
 * @author Bolaji
 * created on 27/08/2023
 */

/*This is a third party library for reducing boilerplates, 
*@Data--- handle the getter and setters
@AllArgsConstructor-- creates a constructor with all parameters
@NoArgsConstructor-- creates a constructor with no argument/parameter in the class
@ToString-- create a toString() method that prints the object as String 
 * **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

/**
 * This class contains the record of items that are been borrowed
 *
 */

public class BorrowedItemRequest {
        //use the item class as part of the parameter in the borrowed item class
        private Item item;

        //id of the user 
        @NotEmpty(message = "UserId cannot be empty")
        private String userId;

        //Name of the user that borrowed an item
        @NonNull
        private String userName;

}
