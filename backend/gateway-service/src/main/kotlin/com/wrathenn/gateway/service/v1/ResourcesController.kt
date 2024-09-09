package com.wrathenn.gateway.service.v1

import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
@RequestMapping("/resources")
@CrossOrigin
class ResourcesController {
    @GetMapping
    fun downloadApk(): ResponseEntity<InputStreamResource> {
        val apkFile = File("/app/apk/app-release-unsigned.apk")

        if (!apkFile.exists()) {
            return ResponseEntity.notFound().build()
        }

        val resource = InputStreamResource(apkFile.inputStream())

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${apkFile.name}")
            .contentLength(apkFile.length())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(resource)
    }
}