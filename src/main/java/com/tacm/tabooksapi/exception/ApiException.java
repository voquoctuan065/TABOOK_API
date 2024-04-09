package com.tacm.tabooksapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends Exception{
    private String message;
    private HttpStatus status;
}
