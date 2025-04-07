package me.vgolovnin.ddd.delivery.adapters.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatusChangeEvent
import me.vgolovnin.ddd.delivery.core.ports.MessageBus
import org.springframework.kafka.core.KafkaOperations

class KafkaMessageBus(
    private val kafkaOperations: KafkaOperations<String, String>,
    private val jsonMapper: ObjectMapper,
) : MessageBus {

    override suspend fun publish(message: OrderStatusChangeEvent) {
        val dto = jsonMapper.writeValueAsString(
            OrderStatusChangeEventDto(
                message.orderId.toString(),
                message.status.toString()
            )
        )
        kafkaOperations.send("order.status.changed", dto)
    }

    private data class OrderStatusChangeEventDto(val orderId: String, val orderStatus: String)
}