package com.sarpio.shop.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends IllegalArgumentException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
        super(msg);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss.SSS");
        String timeStamp = now.format(formatter);
        System.err.println(timeStamp + " " + msg);
    }
}
