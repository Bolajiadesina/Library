package com.berenberg.library.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
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
public class User {
   


    //Name of the user
     @NonNull
    private String userName;

    //Id of the user
    @NotEmpty(message = "UserId cannot be empty")
    private int userId;
    
}
