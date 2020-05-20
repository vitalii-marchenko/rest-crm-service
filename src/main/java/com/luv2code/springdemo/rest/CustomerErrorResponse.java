package com.luv2code.springdemo.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
