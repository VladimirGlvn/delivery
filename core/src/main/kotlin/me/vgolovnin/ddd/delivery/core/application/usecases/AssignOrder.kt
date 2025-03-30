package me.vgolovnin.ddd.delivery.core.application.usecases

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.withError
import me.vgolovnin.ddd.delivery.core.domain.services.Dispatcher
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Fault
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork

object AssignOrderCommand

class AssignOrderHandler(
    private val courierRepository: CourierRepository,
    private val orderRepository: OrderRepository,
    private val dispatcher: Dispatcher,
    private val unitOfWork: UnitOfWork,
) {

    fun handle(@Suppress("UNUSED_PARAMETER") assignOrderCommand: AssignOrderCommand): Either<Fault, Unit> = either {
        val order = orderRepository.findFirstNotDispatched()
        if (order != null) {
            val couriers = courierRepository.findAllFree()
            val assignedCourier = withError({ it }) {
                dispatcher.dispatch(order, couriers.toList()).bind()
            }
            unitOfWork {
                courierRepository.update(assignedCourier)
                orderRepository.update(order)
            }
        }
    }

}