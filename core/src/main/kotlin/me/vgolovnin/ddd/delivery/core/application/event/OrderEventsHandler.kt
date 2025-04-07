package me.vgolovnin.ddd.delivery.core.application.event

import dev.ceviz.message.MessageHandler
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatusChangeEvent
import me.vgolovnin.ddd.delivery.core.ports.MessageBus

class OrderEventsHandler(private val messageBus: MessageBus) : MessageHandler<OrderStatusChangeEvent> {

    override suspend fun handle(message: OrderStatusChangeEvent) {
        messageBus.publish(message)
    }
}