package me.vgolovnin.ddd.delivery.core.domain.services

import arrow.core.Either
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Fault

fun interface Dispatcher {
    fun dispatch(order: Order, couriers: List<Courier>): Either<Fault, Courier>

    object Faults {
        object NoSuitableCouriers : Fault
        object OrderIsAlreadyDispatched : Fault
    }
}