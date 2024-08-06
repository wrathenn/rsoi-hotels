package com.wrathenn.stats.service.services

import com.wrathenn.stats.service.repositories.StatRepository
import com.wrathenn.util.db.transact
import com.wrathenn.util.models.statistics.Stat
import com.wrathenn.util.models.statistics.StatData
import com.wrathenn.util.models.statistics.StatTemplate
import org.jdbi.v3.core.Jdbi
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

interface StatsService {
    fun insertStat(statTemplate: StatTemplate<*>)
    fun getStats(statTypes: List<StatData.Type>): List<Stat<*>>
}

@Service
class StatsServiceImpl(
    private val jdbi: Jdbi,
    private val statRepository: StatRepository,
) : StatsService {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun insertStat(statTemplate: StatTemplate<*>) = jdbi.transact {
        logger.info("inserting stat in service")
        statRepository.insert(statTemplate)
    }

    override fun getStats(statTypes: List<StatData.Type>): List<Stat<*>> = jdbi.transact {
        val stats = statRepository.findStatsByTypes(statTypes)
        logger.info("Got $stats")
        stats
    }
}
