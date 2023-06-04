package com.flab.artshare.config.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun validIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<*> {
        return ErrorResponse
            .from(HttpStatus.BAD_REQUEST, ex)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun validIllegalStateException(ex: IllegalStateException): ResponseEntity<*> {
        return ErrorResponse
            .from(HttpStatus.BAD_REQUEST, ex)
    }

    @ExceptionHandler(BindException::class)
    fun validBindException(ex: BindException): ResponseEntity<*> {
        return ErrorResponse
            .from(HttpStatus.BAD_REQUEST, ex)
    }

    @ExceptionHandler(UnsupportedOperationException::class)
    fun validUnsupportedOperationException(ex: UnsupportedOperationException): ResponseEntity<*> {
        return ErrorResponse
            .from(HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    @ExceptionHandler(IOException::class)
    fun validIOException(ex: IOException): ResponseEntity<*> {
        return ErrorResponse
            .from(HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }
}
