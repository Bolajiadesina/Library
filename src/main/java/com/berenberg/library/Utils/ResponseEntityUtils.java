package com.berenberg.library.Utils;



import com.berenberg.library.dto.generalResponse.GeneralResponseDto;
import com.berenberg.library.dto.generalResponse.GeneralResponseEnum;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * @author Bolaji
 * created on 26/08/2023
 */


public class ResponseEntityUtils {


    private ResponseEntityUtils() {}

    /**
     * Returns invalid parameters response entity
     * @param code: optional code
     * @param message: optional message
     * @return the response entity
     */
    public static ResponseEntity<GeneralResponseDto<Void>> invalidParameters(String code, String message){

        return ResponseEntity.badRequest().body(
                new GeneralResponseDto<Void>()
                        .setCode((code == null) ? GeneralResponseEnum.INVALID_PARAMETERS.getCode() : code)
                        .setMessage((StringUtils.isEmptyBlank(message)) ? "Invalid parameters supplied" : message)
        );
    }


    /**
     * Returns not found response entity
     * @param code: the code
     * @param message the message
     * @return the response entity
     */
    public static ResponseEntity<GeneralResponseDto<Void>> notFound(String code, String message){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new GeneralResponseDto<Void>()
                        .setCode((code == null) ? GeneralResponseEnum.NOT_FOUND.getCode() : code)
                        .setMessage((StringUtils.isEmptyBlank(message)) ? GeneralResponseEnum.NOT_FOUND.getMessage() : message)
        );
    }


    /**
     * Returns not found response entity
     * @param code: the code
     * @param message the message
     * @return the response entity
     */
    public static ResponseEntity<GeneralResponseDto<Void>> alreadyExist(String code, String message){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new GeneralResponseDto<Void>()
                        .setCode((code == null) ? GeneralResponseEnum.ALREADY_EXIST.getCode() : code)
                        .setMessage((StringUtils.isEmptyBlank(message)) ? GeneralResponseEnum.ALREADY_EXIST.getMessage() : message)
        );
    }

    /**
     * Returns internal server error exception
     * @param generalResponseEnum: the general response
     * @param message: the message
     * @return the response entity
     */
    public static ResponseEntity<GeneralResponseDto<Void>> generalException(GeneralResponseEnum generalResponseEnum, String message){

        GeneralResponseDto<Void> res =  new GeneralResponseDto<Void>()
                .setCode(generalResponseEnum.getCode())
                .setMessage(StringUtils.isEmptyBlank(message)? generalResponseEnum.getMessage() : message);

        return (generalResponseEnum.getCode().equals(GeneralResponseEnum.ERROR.getCode())) ?
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res) :
                ResponseEntity.badRequest().body(res);
    }


    public static ResponseEntity<GeneralResponseDto<Void>> unauthorized(String code, String message){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new GeneralResponseDto<Void>()
                        .setCode((StringUtils.isEmptyBlank(code)) ? GeneralResponseEnum.UNAUTHORIZED.getCode() : code)
                        .setMessage((StringUtils.isEmptyBlank(message)) ? "Unauthorized" : message)
        );
    }


    /**
     * Returns successful response for controllers with method return holders
     * @param payload: the payload
     * @param <T>: the type
     * @return the response
     */
    public static <T> ResponseEntity<GeneralResponseDto<T>> getSuccessfulControllerResponse(T payload){

        return ResponseEntity.ok().body(
                new GeneralResponseDto<T>()
                        .setCode(GeneralResponseEnum.SUCCESS.getCode())
                        .setMessage(GeneralResponseEnum.SUCCESS.getMessage())
                        .setPayload(payload)
        );
    }


    /**
     * Returns successful response for controllers with method return holders and sets content-type and headers
     * @param resource: the resource
     * @return the response
     */
    public static <T> ResponseEntity<byte[]> getSuccessfulControllerResourceResponse(byte[] resource, String contentType, String headerName, String ... headerValues){

        if(StringUtils.isEmptyBlank(headerName)) {
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(headerName, headerValues)
                    .body(resource);
        }
    }


    /**
     * Returns successful response for controllers with method return holders
     * @param payload: the payload
     * @param customMessage: custom message to send
     * @param <T>: the type
     * @return the response
     */
    public static <T> ResponseEntity<GeneralResponseDto<T>> getSuccessfulControllerResponse(T payload, String customMessage){

        return ResponseEntity.ok().body(
                new GeneralResponseDto<T>()
                        .setCode(GeneralResponseEnum.SUCCESS.getCode())
                        .setMessage(StringUtils.isEmptyBlank(customMessage) ? GeneralResponseEnum.SUCCESS.getMessage() : customMessage)
                        .setPayload(payload)
        );
    }


    /**
     * Returns successful response for controllers with general response holder
     * @param payload: the general responsepayload
     * @param <T>: the type
     * @return the response
     */
    public static <T> ResponseEntity<GeneralResponseDto<T>> getSuccessfulControllerGeneralResponse(GeneralResponseDto<T> payload){

        return ResponseEntity.ok().body(payload);
    }


    /**
     * Returns a successful response entity
     * @param payload: the payload
     * @param <T>: the type
     * @return the response entity
     */
    public static <T> ResponseEntity<T> successful(Class<T> payload){
        return ResponseEntity.ok().body(payload.cast(payload));
    }
}
