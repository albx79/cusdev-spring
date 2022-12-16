package it.albx79.telepass.cusdev;

import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;

public class TelepassCusdevServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelepassCusdevServerApplication.class, args);
    }

    @ControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(value
                = NoSuchElementException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public @ResponseBody ErrorResponse
        handleException(NoSuchElementException ex) {
            return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, firstNonNull(ex.getMessage(), "")).build();
        }
    }
}
