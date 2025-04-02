package me.vgolovnin.ddd.delivery.api.config

import me.vgolovnin.ddd.delivery.core.application.usecases.command.CreateOrderHandler
import me.vgolovnin.ddd.delivery.core.ports.GeoGateway
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CreateOrderConfiguration {

    @Bean
    fun createOrderHandler(orderRepository: OrderRepository, geoGateway: GeoGateway) =
        CreateOrderHandler(orderRepository, geoGateway)
}