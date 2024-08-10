package com.wrathenn.gateway.service.models

import com.wrathenn.util.models.statistics.Stat
import com.wrathenn.util.models.statistics.StatData
import java.time.Instant

data class StatDto(
    val id: Long,
    val ts: Instant,
    val data: StatData,
) {
    companion object {
        fun fromModel(stat: Stat) = StatDto(id = stat.id, ts = stat.ts, data = stat.data)
    }
}
