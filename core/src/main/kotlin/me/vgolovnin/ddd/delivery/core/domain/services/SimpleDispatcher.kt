package me.vgolovnin.ddd.delivery.core.domain.services

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.ensureNotNull
import arrow.core.raise.withError
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Fault

class SimpleDispatcher : Dispatcher {

    override fun dispatch(order: Order, couriers: List<Courier>): Either<Fault, Courier> = either {
        ensure(couriers.isNotEmpty()) { Dispatcher.Faults.NoSuitableCouriers }
        ensure(order.status == OrderStatus.CREATED) { Dispatcher.Faults.OrderIsAlreadyDispatched }

        val fastestCourier = couriers.filter { it.isFree }.minByOrNull { it.timeTo(order.location) }
        ensureNotNull(fastestCourier) { Dispatcher.Faults.NoSuitableCouriers }

        withError({ it }) {
            order.assignTo(fastestCourier).bind()
        }

        fastestCourier.apply { isFree = false }
    }
}