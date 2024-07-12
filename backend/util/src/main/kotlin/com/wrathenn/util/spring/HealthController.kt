package com.wrathenn.util.spring

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/manage/health")
    fun health() = ResponseEntity<Unit>(HttpStatusCode.valueOf(200))
}