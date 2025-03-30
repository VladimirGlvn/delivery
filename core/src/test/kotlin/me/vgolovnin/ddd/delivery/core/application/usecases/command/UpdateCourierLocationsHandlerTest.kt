package me.vgolovnin.ddd.delivery.core.application.usecases.command

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.model.order.OrderStatus
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import me.vgolovnin.ddd.delivery.core.ports.OrderRepository
import me.vgolovnin.ddd.delivery.core.utils.UnitOfWork
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@DisplayName("Update courier locations use case should")
@ExtendWith(MockKExtension::class)
class UpdateCourierLocationsHandlerTest {

    @MockK
    private lateinit var courierRepository: CourierRepository

    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK
    private lateinit var unitOfWork: UnitOfWork

    @InjectMockKs
    private lateinit var handler: UpdateCourierLocationsHandler

    @BeforeEach
    fun setUp() {
        every { unitOfWork.invoke(any()) } answers { firstArg<() -> Unit>()() }
        every { courierRepository.update(any()) } just runs
        every { orderRepository.update(any()) } just runs
    }

    @Test
    @DisplayName("Update assigned courier location by one step")
    fun `update courier location by one step`() {
        val courier = Courier("alex", "bicycle", 1, Location(1, 1)).apply { isFree = false }
        val order = Order(UUID.randomUUID(), Location(3, 1)).also { it.assignTo(courier) }
        every { orderRepository.findAllAssigned() } returns listOf(order)
        every { courierRepository.findById(courier.id) } returns courier

        handler.handle(UpdateCourierLocationsCommand)

        courier.location shouldBe Location(2, 1)
        courier.isFree shouldBe false
        order.status shouldBe OrderStatus.ASSIGNED
        verify { courierRepository.update(courier) }
    }

    @Test
    @DisplayName("Complete order if courier is at order's location")
    fun `complete order`() {
        val courier = Courier("alex", "bicycle", 1, Location(1, 1)).apply { isFree = false }
        val order = Order(UUID.randomUUID(), Location(2, 1)).also { it.assignTo(courier) }
        every { orderRepository.findAllAssigned() } returns listOf(order)
        every { courierRepository.findById(courier.id) } returns courier

        handler.handle(UpdateCourierLocationsCommand)

        courier.isFree shouldBe true
        order.status shouldBe OrderStatus.COMPLETED
        verify { courierRepository.update(courier) }
        verify { orderRepository.update(order) }
    }
}