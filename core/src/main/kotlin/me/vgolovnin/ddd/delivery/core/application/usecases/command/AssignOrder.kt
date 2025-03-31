package me.vgolovnin.ddd.delivery.core.application.usecases.command

import arrow.core.getOrElse
import dev.ceviz.command.Command
import dev.ceviz.command.CommandHandler
import me.vgolovnin.ddd.delivery.core.domain.services.Dispatcher
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork

object AssignOrderCommand : Command

class NoSuitableCouriersException : Exception()

class AssignOrderHandler(
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository,
    private val dispatcher: Dispatcher,
    private val unitOfWork: UnitOfWork,
) : CommandHandler<AssignOrderCommand> {

    override suspend fun handle(command: AssignOrderCommand) {
        val order = orderRepository.findFirstNotDispatched()
        if (order != null) {
            val couriers = courierRepository.findAllFree()
            if (couriers.isEmpty()) throw NoSuitableCouriersException()
            val assignedCourier = dispatcher.dispatch(order, couriers.toList())
                    .getOrElse { throw RuntimeException(it.javaClass.name) }

            unitOfWork {
                courierRepository.update(assignedCourier)
                orderRepository.update(order)
            }
        }
    }

}