
package com.berenberg.library.exceptions;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.berenberg.library.Utils.ResponseEntityUtils;
import com.berenberg.library.dto.generalResponse.GeneralResponseDto;
import com.berenberg.library.dto.generalResponse.GeneralResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
/**
 * @author Bolaji
 * created on 27/08/2023
 */
@RestControllerAdvice
@Slf4j
public class ControllerExceptionInterceptor {

    /**
     * Handles method validation exceptions
     * @param ex: the exception
     * @return the response entity
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<GeneralResponseDto<Void>> handleMethodValidations(MethodArgumentNotValidException ex){

        String errors = ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" | "));


        return ResponseEntityUtils.invalidParameters(null, errors);
    }

    /**
     * Handles constraint validation exceptions
     * @param ex: the exception
     * @return the response entity
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<GeneralResponseDto<Void>> handleConstraintValidations(ConstraintViolationException ex){

        String errors = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(" | "));


        return ResponseEntityUtils.invalidParameters(null, errors);
    }



    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    protected ResponseEntity<GeneralResponseDto<Void>> handleInvalidRequestBody(HttpMessageNotReadableException ex){

        return ResponseEntityUtils.invalidParameters(null, "The request payload/body is poorly formatted (invalid json)");
    }


    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<GeneralResponseDto<Void>> customExceptionHandler(Exception e){

        log.error(e.getMessage(), e);
        return ResponseEntityUtils.generalException(GeneralResponseEnum.ERROR, (e instanceof LibraryException) ? e.getMessage() : null);
    }
}
