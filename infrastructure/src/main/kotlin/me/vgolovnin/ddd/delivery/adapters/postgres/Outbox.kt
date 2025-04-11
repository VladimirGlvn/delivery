package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatusChangeEvent

internal interface Outbox {
    fun send(message: OrderStatusChangeEvent)
}