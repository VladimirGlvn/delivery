package me.vgolovnin.ddd.delivery.core.domain.services

import arrow.core.left
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.matchers.shouldBe
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("SimpleDispatcher should")
class SimpleDispatcherTest {

    private val dispatcher = SimpleDispatcher()

    @Test
    @DisplayName("dispatch order to the first courier at place")
    fun `dispatch order`() {
        val order = Order(UUID.randomUUID(), location = Location(1, 1))
        val courier1 = Courier("a", "snail", 1, order.location)
        val courier2 = Courier("b", "car", 3, Location(2, 1))

        val result = dispatcher.dispatch(order, listOf(courier1, courier2))

        result shouldBeRight courier1
        courier1.isFree shouldBe false
        courier2.isFree shouldBe true
    }

    @Test
    @DisplayName("dispatch order to the first closest courier")
    fun `dispatch order to closest courier`() {
        val order = Order(UUID.randomUUID(), location = Location(1, 1))
        val courier1 = Courier("a", "snail", 1, Location(1, 2))
        val courier2 = Courier("b", "car", 3, Location(1, 2))

        val result = dispatcher.dispatch(order, listOf(courier1, courier2))

        result shouldBeRight courier1
        courier1.isFree shouldBe false
        courier2.isFree shouldBe true
    }

    @Test
    @DisplayName("dispatch order to the fastest courier")
    fun `dispatch order to fastest courier`() {
        val order = Order(UUID.randomUUID(), location = Location(1, 1))
        val courier1 = Courier("a", "skate", 1, Location(2, 2))
        val courier2 = Courier("b", "bike", 2, Location(2, 2))

        val result = dispatcher.dispatch(order, listOf(courier1, courier2))

        result shouldBeRight courier2
        order.courierId shouldBe courier2.id
        order.status shouldBe OrderStatus.ASSIGNED
        courier2.isFree shouldBe false
        courier1.isFree shouldBe true
    }

    @Test
    @DisplayName("not dispatch order to busy couriers")
    fun `not dispatch order to busy couriers`() {
        val order = Order(UUID.randomUUID(), location = Location(1, 1))
        val courier1 = Courier("a", "skate", 1, Location(2, 2))
        val courier2 = Courier("b", "bike", 2, Location(2, 2)).also { it.isFree = false }

        val result = dispatcher.dispatch(order, listOf(courier1, courier2))

        result shouldBeRight courier1
        courier1.isFree shouldBe false
        courier2.isFree shouldBe false
    }

    @Test
    @DisplayName("reject order if it is already dispatched")
    fun `reject order if it is already dispatched`() {
        val courier = Courier("a", "skate", 1, Location(2, 2))
        val otherCourier = Courier("b", "bike", 2, Location(2, 2))
        val order = Order(UUID.randomUUID(), location = Location(1, 1))
            .apply { assignTo(courier) }

        val result = dispatcher.dispatch(order, listOf(otherCourier))

        result shouldBe Dispatcher.Faults.OrderIsAlreadyDispatched.left()
    }

    @Test
    @DisplayName("return error if couriers list is empty")
    fun `couriers list is empty`() {
        val order = Order(UUID.randomUUID(), location = Location(1, 1))

        val result = dispatcher.dispatch(order, emptyList())

        result shouldBeLeft Dispatcher.Faults.NoSuitableCouriers
    }

    @Test
    @DisplayName("return error if there is no suitable courier")
    fun `return error if there is no matching courier`() {
        val order = Order(UUID.randomUUID(), location = Location(1, 1))
        val courier1 = Courier("a", "skate", 1, Location(2, 2)).also { it.isFree = false }
        val courier2 = Courier("b", "bike", 2, Location(1, 2)).also { it.isFree = false }

        val result = dispatcher.dispatch(order, listOf(courier1, courier2))

        result shouldBeLeft Dispatcher.Faults.NoSuitableCouriers
    }
}