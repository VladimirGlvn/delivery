package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import java.util.*
import kotlin.jvm.optionals.getOrNull

internal class PostgresOrderRepository(
    private val jdbcOrderRepository: JdbcOrderRepository
) : OrderRepository {

    override fun add(order: Order) {
        jdbcOrderRepository.save(
            order.toRecord().also { it.new = true }
        )
    }

    override fun update(order: Order) {
        jdbcOrderRepository.save(order.toRecord())
    }

    override fun findById(id: UUID): Order? {
        return jdbcOrderRepository.findById(id).map(::toEntity).getOrNull()
    }


    override fun findFirstNotDispatched(): Order? =
        jdbcOrderRepository.findFirstByStatus(OrderStatus.CREATED).map(::toEntity).getOrNull()

    override fun findAllAssigned(): Iterable<Order> =
        jdbcOrderRepository.findAllByStatus(OrderStatus.ASSIGNED).map(::toEntity)

    override fun findAll(): Iterable<Order> = jdbcOrderRepository.findAll().map(::toEntity)

    private fun toEntity(record: OrderRecord) = with(record) {
        Order(id, Location(locationX, locationY), status, courierId)
    }

    private fun Order.toRecord() = OrderRecord(id, location.x, location.y, status, courierId)
}