package com.wrathenn.util.exceptions

import java.lang.Exception
import java.time.Instant

data class ErrorDescription(
    val ts: Instant,
    val route: String,
    val message: String?,
    val cause: Throwable?,
)

open class ApiException(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message, cause)

class ResourceNotFoundException(
    override val message: String,
    override val cause: Throwable? = null,
) : ApiException(message, cause)

class BadRequestException(
    override val message: String,
    override val cause: Throwable? = null,
) : ApiException(message, cause)

class ServiceUnavailableException(
    override val message: String,
    override val cause: Throwable? = null,
) : ApiException(message, cause)
