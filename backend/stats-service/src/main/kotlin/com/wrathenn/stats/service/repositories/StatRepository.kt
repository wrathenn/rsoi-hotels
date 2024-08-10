package com.wrathenn.stats.service.repositories

import com.wrathenn.stats.service.repositories.entities.StatEntity
import com.wrathenn.stats.service.repositories.entities.StatInsertableEntity
import com.wrathenn.util.models.statistics.Stat
import com.wrathenn.util.models.statistics.StatData
import com.wrathenn.util.models.statistics.StatTemplate
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.bindKotlin
import org.jdbi.v3.core.kotlin.mapTo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

interface StatRepository {
    context(Handle) fun insert(stat: StatTemplate<*>)
    context(Handle) fun findStatsByTypes(types: List<StatData.Type>): List<Stat>
}

@Component
class StatRepositoryImpl : StatRepository {
    private val logger = LoggerFactory.getLogger(this::class.java)

    context(Handle)
    override fun insert(stat: StatTemplate<*>) {
        logger.info("Inserting stat in repository")

        val id = createQuery("""
            insert into stats.stats(ts, data)
            values (:ts, :data)
            returning id
        """.trimIndent())
            .bindKotlin(StatInsertableEntity(ts = stat.ts, data = stat.data))
            .mapTo<Long>()
            .one()
        logger.info("Inserted with id = $id")
    }

    context(Handle)
    override fun findStatsByTypes(types: List<StatData.Type>): List<Stat> {
        if (types.isEmpty()) return emptyList()

        select("""
            explain analyze
            select id, ts, data
            from stats.stats
            where data->>'type' = any(:types)
            order by ts desc
        """.trimIndent())
            .bindArray("types", String::class.java, types.map { it.toString() }.toTypedArray())
            .mapTo<String>()
            .list()
            .forEach(::println)

        return select("""
            select id, ts, data
            from stats.stats
            where data->>'type' = any(:types)
            order by ts desc
        """.trimIndent())
            .bindArray("types", String::class.java, types.map { it.toString() }.toTypedArray())
            .mapTo<StatEntity>()
            .list()
            .map {
                Stat(id = it.id, ts = it.ts, data = it.data)
            }
    }
}
