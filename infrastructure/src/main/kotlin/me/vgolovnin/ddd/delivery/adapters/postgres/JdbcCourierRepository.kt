package me.vgolovnin.ddd.delivery.adapters.postgres

import org.springframework.data.repository.CrudRepository
import java.util.*

interface JdbcCourierRepository: CrudRepository<CourierRecord, UUID> {
    fun findAllByFree(free: Boolean): Collection<CourierRecord>
}
