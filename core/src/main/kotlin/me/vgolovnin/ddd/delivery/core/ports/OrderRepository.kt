package me.vgolovnin.ddd.delivery.core.ports

import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import java.util.*

interface OrderRepository {

    fun add(order: Order)
    fun update(order: Order)
    fun findById(id: UUID): Order?
    fun findFirstNotDispatched(): Order?
    fun findAll(): Iterable<Order>
}