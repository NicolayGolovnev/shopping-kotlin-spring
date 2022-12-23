package ru.golovnev.shopping.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class MyExceptionHandler {
    @ExceptionHandler(value = [Exception::class])
    fun responseEntityException(exception: Exception): ResponseEntity<String> =
         ResponseEntity<String>(
            "Произошла ошибка: ${exception.message}",
            HttpStatus.INTERNAL_SERVER_ERROR
        )
}