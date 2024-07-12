package com.wrathenn.util.spring

import com.wrathenn.util.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.time.Instant

@ControllerAdvice
class ApiExceptionHandler {
    private fun getErrorDescription(request: WebRequest, cause: ApiException?): ErrorDescription {
        val path = (request as? ServletWebRequest)?.request?.requestURI?.toString() ?: "Unknown"
        return ErrorDescription(Instant.now(), path, cause?.message, cause)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(e: BadRequestException, request: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(getErrorDescription(request, e))
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(e: ResourceNotFoundException, request: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(getErrorDescription(request, e))
    }

    @ExceptionHandler(ServiceUnavailableException::class)
    fun handleServiceUnavailable(e: ServiceUnavailableException, request: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(getErrorDescription(request, e))
    }

    @ExceptionHandler(ApiException::class)
    fun handleGeneralApiException(e: ApiException, request: WebRequest): ResponseEntity<ErrorDescription> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(getErrorDescription(request, e))
    }
}