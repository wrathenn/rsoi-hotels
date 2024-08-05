package com.wrathenn.stats.service.repositories.entities

import com.wrathenn.util.models.statistics.StatData
import org.jdbi.v3.json.Json
import java.time.Instant

data class StatEntity(
    val id: Long,
    val ts: Instant,
    @Json
    val data: StatData,
)

data class StatInsertableEntity(
    val ts: Instant,
    @Json
    val data: StatData,
)
