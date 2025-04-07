package me.vgolovnin.ddd.delivery.api.config

import me.vgolovnin.ddd.delivery.core.application.event.OrderEventsHandler
import me.vgolovnin.ddd.delivery.core.ports.MessageBus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderEventsHandlerConfiguration {

    @Bean
    fun orderEventsHandler(messageBus: MessageBus) = OrderEventsHandler(messageBus)
}