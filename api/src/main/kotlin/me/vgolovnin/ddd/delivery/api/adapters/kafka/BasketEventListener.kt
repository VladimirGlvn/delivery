package me.vgolovnin.ddd.delivery.api.adapters.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import dev.ceviz.Mediator
import kotlinx.coroutines.runBlocking
import me.vgolovnin.ddd.delivery.core.application.usecases.command.CreateOrderCommand
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.*


@Component
class BasketEventListener(
    private val mediator: Mediator,
    private val objectMapper: ObjectMapper,
) {
    private val log = LoggerFactory.getLogger(BasketEventListener::class.java)

    @KafkaListener(topics = ["basket.confirmed"])
    fun handleMessage(record: ConsumerRecord<String, String>) = runBlocking {
        val event = objectMapper.readValue(record.value(), BasketConfirmedIntegrationEvent::class.java)
        log.info("Basket confirmed event received: {}", event)
        mediator.send(CreateOrderCommand(UUID.fromString(event.basketId), event.address.street))
    }
}