package me.vgolovnin.ddd.delivery.core.application.usecases

import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository

class CreateOrderHandler(
    private val orderRepository: OrderRepository,
) {

    fun handle(cmd: CreateOrderCommand) {
        //todo Use geo-service to translate cmd.street into order location.
        val order = Order(id = cmd.basketId, location = Location.random())
        orderRepository.add(order)
    }
}