package com.koraei.bank.service.exception

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.Locale


@ControllerAdvice
class GlobalExceptionHandler @Autowired constructor(
    private val messageSource: MessageSource
) {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<String> {
        val errorMessage = ex.bindingResult.allErrors.joinToString { it.defaultMessage ?: "" }
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(GeneralException::class)
    fun handleGeneralException(ex: GeneralException, locale: Locale): ResponseEntity<String> {
        val errorMessage = messageSource.getMessage(ex.message!!, null, locale)
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<String> {
        val errorMessage = ex.message ?: "An unexpected error occurred"
        return ResponseEntity(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

class GeneralException(
    message: String
) : RuntimeException(message)

