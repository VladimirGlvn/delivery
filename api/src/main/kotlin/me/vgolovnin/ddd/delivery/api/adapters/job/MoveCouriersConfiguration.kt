package me.vgolovnin.ddd.delivery.api.adapters.job

import me.vgolovnin.ddd.delivery.core.application.usecases.command.UpdateCourierLocationsHandler
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MoveCouriersConfiguration {

    @Bean
    fun moveCouriersHandler(
        courierRepository: CourierRepository,
        orderRepository: OrderRepository,
        unitOfWork: UnitOfWork,
    ) = UpdateCourierLocationsHandler(courierRepository, orderRepository, unitOfWork)
}