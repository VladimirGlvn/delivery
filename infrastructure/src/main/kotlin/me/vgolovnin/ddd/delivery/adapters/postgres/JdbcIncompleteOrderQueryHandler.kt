package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.application.usecases.query.*
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import java.util.*

internal class JdbcIncompleteOrderQueryHandler(
    private val orderRepository: JdbcOrderRepository,
) : IncompleteOrderQueryHandler {

    override fun handle(query: IncompleteOrdersQuery): IncompleteOrdersResponse =
        orderRepository.findAllByStatusIn(EnumSet.of(OrderStatus.CREATED, OrderStatus.ASSIGNED))
            .map(::toView)
            .let { IncompleteOrdersResponse(it) }

    private fun toView(orderRecord: OrderRecord): Order = with(orderRecord) {
        Order(id, Location(locationX, locationY))
    }
}