package com.mdubravac.fonts.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class ApplicationException extends RuntimeException {

    private final HttpStatus code;
    public ApplicationException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }
}
