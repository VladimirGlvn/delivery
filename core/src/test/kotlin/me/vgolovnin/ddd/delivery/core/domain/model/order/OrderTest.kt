package me.vgolovnin.ddd.delivery.core.domain.model.order

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.properties.shouldHaveValue
import io.kotest.matchers.shouldBe
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order.Faults.AlreadyAssigned
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order.Faults.NotAssignedYet
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus.*
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

@DisplayName("Order should")
class OrderTest {

    private val courier = Courier("Ivan", transportName = "car", transportSpeed = 3, location = Location.random())

    @Test
    fun `have CREATED status when just created`() {
        val order = createOrder()
        order.status shouldBe CREATED
    }

    @Test
    fun `switch to ASSIGNED status when assigned to a courier`() {
        val order = createOrder()

        val result = order.assignTo(courier)

        result.shouldBeRight()
        assertSoftly(order) {
            courierId shouldBe courier.id
            status shouldBe ASSIGNED
        }
    }

    @Test
    fun `not be assigned if it is completed`() {
        val order = createCompletedOrder()

        val result = order.assignTo(courier)

        result shouldBeLeft AlreadyAssigned
    }

    @Test
    fun `not be assigned if it is already assigned`() {
        val order = createOrder()
        order.assignTo(courier)
        val anotherCourier =
            Courier("Viktor", transportName = "skate", transportSpeed = 2, location = Location.random())

        val result = order.assignTo(anotherCourier)

        assertSoftly(order) {
            result shouldBeLeft AlreadyAssigned
            order.courierId shouldBe courier.id
            order.status shouldBe ASSIGNED
        }
    }

    @Test
    fun `switch to COMPLETED status when completed`() {
        val order = createCompletedOrder()

        order::status shouldHaveValue COMPLETED
    }

    @Test
    fun `not allow completion of unassigned order`() {
        val unassignedOrder = createOrder()

        val result = unassignedOrder.complete()

        result shouldBeLeft NotAssignedYet
        unassignedOrder.status shouldBe CREATED
    }

    @Test
    fun `stay in COMPLETED status if completed another time`() {
        val order = createCompletedOrder()

        val result = order.complete()

        result.shouldBeRight()
        order::status shouldHaveValue COMPLETED
    }

    @Test
    fun `have null courierId until assigned to a courier`() {
        val order = createOrder()

        order::courierId shouldHaveValue null
    }

    private fun createOrder() = Order(UUID.randomUUID(), Location.random())

    private fun createCompletedOrder(): Order {
        val order = createOrder()
        val courier = Courier("Peter", transportName = "bike", transportSpeed = 1, location = Location.random())
        order.assignTo(courier)
        order.complete()
        return order
    }
}