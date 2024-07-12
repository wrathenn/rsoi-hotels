package com.wrathenn.gateway.service.clients

import com.wrathenn.util.exceptions.ServiceUnavailableException
import com.wrathenn.util.models.loyalty.Loyalty
import com.wrathenn.util.models.loyalty.LoyaltyReservationCountOperation
import feign.RetryableException
import feign.Retryer
import io.github.resilience4j.retry.annotation.Retry
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.context.annotation.Primary
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Component
class LoyaltyRetryer: Retryer {
    override fun clone(): Retryer {
        return LoyaltyRetryer()
    }

    override fun continueOrPropagate(e: RetryableException?) {
        try {
            Thread.sleep(10000)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw e
        }
    }

}

@FeignClient(
    value = "loyalty-service",
    url = "\${app.interop.loyalty-client-api}",
    fallback = LoyaltyClientFallback::class,
)
@Primary
interface LoyaltyClient {
    @GetMapping("/loyalties")
    fun getLoyalty(@RequestHeader("X-User-Name") username: String): Loyalty

    @GetMapping("/loyalties")
    fun getLoyaltyWithFallback(@RequestHeader("X-User-Name") username: String): Loyalty?

    @GetMapping("/loyalties")
    fun getLoyaltyOrNull(@RequestHeader("X-User-Name") username: String): Loyalty?

    @PutMapping("/loyalties/reservationCount")
    fun updateReservationCount(
        @RequestHeader("X-User-Name") username: String,
        @RequestBody loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
    ): Loyalty

    @PutMapping("/loyalties/reservationCount")
//    @Retryable(retryFor = [ Throwable::class ], maxAttempts = 10)
    fun updateReservationCountWithRetry(
        @RequestHeader("X-User-Name") username: String,
        @RequestBody loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
        @RequestHeader /* Will go to body otherwise */ onFail: () -> Unit
    ): Loyalty?
}

@Component
class LoyaltyClientFallback: LoyaltyClient {
    private val unavailableMessage = "Loyalty Service unavailable"

    override fun getLoyalty(username: String): Loyalty {
        throw ServiceUnavailableException(unavailableMessage)
    }

    override fun getLoyaltyWithFallback(username: String): Loyalty? {
        return null
    }

    override fun getLoyaltyOrNull(username: String): Loyalty? {
        return null
    }

    override fun updateReservationCount(username: String, loyaltyReservationCountOperation: LoyaltyReservationCountOperation): Loyalty {
        throw ServiceUnavailableException(unavailableMessage)
    }

    override fun updateReservationCountWithRetry(
        username: String,
        loyaltyReservationCountOperation: LoyaltyReservationCountOperation,
        onFail: () -> Unit,
    ): Loyalty? {
        onFail()
        return null
    }
}
