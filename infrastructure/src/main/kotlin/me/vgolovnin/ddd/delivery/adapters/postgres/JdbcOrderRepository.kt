package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import org.springframework.data.repository.CrudRepository
import java.util.*

internal interface JdbcOrderRepository: CrudRepository<OrderRecord, UUID> {

    fun findFirstByStatus(status: OrderStatus): Optional<OrderRecord>
    fun findAllByStatus(status: OrderStatus): Iterable<OrderRecord>
}