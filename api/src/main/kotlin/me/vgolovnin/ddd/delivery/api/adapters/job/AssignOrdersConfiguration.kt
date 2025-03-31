package me.vgolovnin.ddd.delivery.api.adapters.job

import me.vgolovnin.ddd.delivery.core.application.usecases.command.AssignOrderHandler
import me.vgolovnin.ddd.delivery.core.domain.services.Dispatcher
import me.vgolovnin.ddd.delivery.core.domain.services.SimpleDispatcher
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AssignOrdersConfiguration {

    @Bean
    fun assignOrderHandler(
        courierRepository: CourierRepository,
        orderRepository: OrderRepository,
        dispatcher: Dispatcher,
        unitOfWork: UnitOfWork
    ) = AssignOrderHandler(courierRepository, orderRepository, dispatcher, unitOfWork)

    @Bean
    fun dispatcher() = SimpleDispatcher()

}