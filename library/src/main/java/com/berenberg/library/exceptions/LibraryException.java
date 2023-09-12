package com.berenberg.library.exceptions;

import com.berenberg.library.Utils.StringUtils;
import com.berenberg.library.dto.generalResponse.GeneralResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;



@EqualsAndHashCode(callSuper = true)
@Data
public class LibraryException extends RuntimeException{

    private GeneralResponseEnum exceptionType;
    private String message;
    private String code;
    public LibraryException (GeneralResponseEnum exceptionType, String message) {
        super((!StringUtils.isEmptyBlank(message)) ? message : exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.message = message;
    }
}
