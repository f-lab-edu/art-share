package com.flab.artshare.config.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException

data class ErrorResponse(
    private val status: Int,
    private val message: String,
    private val fieldErrors: List<FieldErrorInfo>? = null,
) {
    data class FieldErrorInfo(val field: String, val error: String)
    companion object {
        fun from(httpStatus: HttpStatus, ex: Exception): ResponseEntity<*> {
            print(ex is BindException)

            val fieldErrors = if (ex is BindException) {
                ex.bindingResult.fieldErrors.map { FieldErrorInfo(it.field, it.defaultMessage ?: "Unknown error") }
            } else {
                null
            }

            val errorResponse = ErrorResponse(
                httpStatus.value(),
                ex.message!!,
                fieldErrors
            )

            return ResponseEntity
                .status(httpStatus)
                .body(errorResponse)
        }
    }
}
