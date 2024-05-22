package com.mdubravac.fonts.config;


import com.mdubravac.fonts.dto.ErrorDto;
import com.mdubravac.fonts.exceptions.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {ApplicationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(ApplicationException ex) {
        return ResponseEntity.status(ex.getCode()).body(ErrorDto.builder().message(ex.getMessage()).build());
    }
}
