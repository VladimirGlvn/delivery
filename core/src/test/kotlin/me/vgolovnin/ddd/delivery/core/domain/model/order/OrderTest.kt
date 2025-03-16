package me.vgolovnin.ddd.delivery.core.domain.model.order

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.properties.shouldHaveValue
import io.kotest.matchers.shouldBe
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
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

        order.assignTo(courier)

        assertSoftly(order) {
            courierId shouldBe courier.id
            status shouldBe ASSIGNED
        }
    }

    @Test
    fun `not be assigned if it is completed`() {
        val order = createCompletedOrder()

        shouldThrow<IllegalStateException> { order.assignTo(courier) }
    }

    @Test
    fun `turn to COMPLETED status when completed`() {
        val order = createCompletedOrder()

        order::status shouldHaveValue COMPLETED
    }

    @Test
    fun `not allow completion of unassigned order`() {
        val unassignedOrder = createOrder()

        shouldThrow<IllegalStateException> {
            unassignedOrder.complete()
        }
    }

    @Test
    fun `stay in COMPLETED status if completed another time`() {
        val order = createCompletedOrder()

        order.complete()

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
        order.assignTo(courier)
        order.complete()
        return order
    }
}