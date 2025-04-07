package me.vgolovnin.ddd.delivery.core.ports

import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatusChangeEvent

interface MessageBus {

    suspend fun publish(message: OrderStatusChangeEvent)
}