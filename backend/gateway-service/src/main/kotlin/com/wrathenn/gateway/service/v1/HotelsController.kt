package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.HotelDto
import com.wrathenn.gateway.service.services.HotelsService
import com.wrathenn.util.models.Page
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/hotels")
@CrossOrigin
class HotelsController(
    val hotelsService: HotelsService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun getPaged(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): Page<HotelDto> {
        System.getenv().map {
            logger.info("${it.key} -- ${it.value}")
        }
        return hotelsService.getHotelsPaged(page, size)
    }
}
