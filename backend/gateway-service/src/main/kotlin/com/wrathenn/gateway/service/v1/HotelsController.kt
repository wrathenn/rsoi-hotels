package com.wrathenn.gateway.service.v1

import com.wrathenn.gateway.service.models.HotelDto
import com.wrathenn.gateway.service.services.HotelsService
import com.wrathenn.util.models.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/hotels")
class HotelsController(
    val hotelsService: HotelsService,
) {
    @GetMapping
    fun getPaged(
        @RequestParam page: Int,
        @RequestParam size: Int
    ): Page<HotelDto> {
        return hotelsService.getHotelsPaged(page, size)
    }
}
