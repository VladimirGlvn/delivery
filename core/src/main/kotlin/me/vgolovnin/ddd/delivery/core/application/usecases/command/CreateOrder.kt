package me.vgolovnin.ddd.delivery.core.application.usecases.command

import dev.ceviz.command.Command
import dev.ceviz.command.CommandHandler
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import java.util.*

data class CreateOrderCommand(
    val basketId: UUID,
    val street: String,
) : Command

class CreateOrderHandler(
    private val orderRepository: OrderRepository,
): CommandHandler<CreateOrderCommand> {

    override suspend fun handle(command: CreateOrderCommand) {
        //todo Use geo-service to translate cmd.street into order location.
        val order = Order(id = command.basketId, location = Location.random())
        orderRepository.add(order)
    }
}