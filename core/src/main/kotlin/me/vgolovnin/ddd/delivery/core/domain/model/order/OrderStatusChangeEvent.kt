package me.vgolovnin.ddd.delivery.core.domain.model.order

import java.util.*

data class OrderStatusChangeEvent(
    val orderId: UUID,
    val status: OrderStatus,
)
