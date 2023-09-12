package com.berenberg.library.dto.generalResponse;

/**
 * @author Bolaji
 *         created on 05/09/2023
 */
public enum GeneralResponseEnum {
    SUCCESS("00", "Operation successful"),
    NO_RESULT("11", "No results were returned"),
    ALREADY_REGISTERED("22", "User already registered"),
    INVALID_PARAMETERS("33", "Invalid information provided"),
    ALREADY_EXIST("44", "The specified resource already exist"),
    NOT_FOUND("55", "Resource not found"),
    UNAUTHORIZED("66", "Unauthorized"),
    ERROR("99", "An error occurred on the server. Please try again later"),
    INVALID_AUTH_PARAMETERS("35", "Invalid Authentication Parameters provided");

    String code, message;

    GeneralResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue(GeneralResponseEnum generalResponseEnum) {
        return generalResponseEnum.message;
    }

    public String getCode() {
        return this.code;
    }

    public static String getCode(GeneralResponseEnum generalResponseEnum) {
        return generalResponseEnum.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static String getMessage(GeneralResponseEnum generalResponseEnum) {
        return generalResponseEnum.message;
    }
}
