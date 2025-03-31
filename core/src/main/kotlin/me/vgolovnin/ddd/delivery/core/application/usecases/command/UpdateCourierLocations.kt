package me.vgolovnin.ddd.delivery.core.application.usecases.command

import dev.ceviz.command.Command
import dev.ceviz.command.CommandHandler
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork

object UpdateCourierLocationsCommand : Command

class UpdateCourierLocationsHandler(
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository,
    private val unitOfWork: UnitOfWork,
) : CommandHandler<UpdateCourierLocationsCommand> {

    override suspend fun handle(command: UpdateCourierLocationsCommand) {
        orderRepository.findAllAssigned().forEach { order ->
            val courier = courierRepository.findById(order.courierId!!)
            checkNotNull(courier) { "Courier assigned to order must exist" }
            courier.moveTo(order.location)

            unitOfWork {
                if (courier.location == order.location) {
                    order.complete()
                    courier.isFree = true

                    orderRepository.update(order)
                }
                courierRepository.update(courier)
            }
        }
    }
}