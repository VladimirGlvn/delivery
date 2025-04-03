package me.vgolovnin.ddd.delivery.core.application.usecases.command

import dev.ceviz.command.Command
import dev.ceviz.command.CommandHandler
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.ports.GeoGateway
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import java.util.*

data class CreateOrderCommand(
    val basketId: UUID,
    val street: String,
) : Command

class CreateOrderHandler(
    private val orderRepository: OrderRepository,
    private val geoGateway: GeoGateway
): CommandHandler<CreateOrderCommand> {

    override suspend fun handle(command: CreateOrderCommand) {
        val location = geoGateway.findStreetLocation(command.street)
        val order = Order(id = command.basketId, location)
        orderRepository.add(order)
    }
}