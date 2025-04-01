package me.vgolovnin.ddd.delivery.adapters.postgres

import dev.ceviz.query.QueryHandler
import me.vgolovnin.ddd.delivery.core.application.usecases.query.IncompleteOrdersQuery
import me.vgolovnin.ddd.delivery.core.application.usecases.query.IncompleteOrdersResponse
import me.vgolovnin.ddd.delivery.core.application.usecases.query.Location
import me.vgolovnin.ddd.delivery.core.application.usecases.query.Order
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
internal class JdbcIncompleteOrderQueryHandler(
    private val orderRepository: JdbcOrderRepository,
) : QueryHandler<IncompleteOrdersQuery, IncompleteOrdersResponse> {

    override suspend fun handle(query: IncompleteOrdersQuery): IncompleteOrdersResponse =
        orderRepository.findAllByStatusIn(EnumSet.of(OrderStatus.CREATED, OrderStatus.ASSIGNED))
            .map(::toView)
            .let { IncompleteOrdersResponse(it) }

    private fun toView(orderRecord: OrderRecord): Order = with(orderRecord) {
        Order(id, Location(locationX, locationY))
    }
}