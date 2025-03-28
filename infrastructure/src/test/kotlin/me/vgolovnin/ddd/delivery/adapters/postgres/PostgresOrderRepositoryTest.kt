package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.model.order.Order
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.test.context.ContextConfiguration
import java.util.*

@DataJdbcTest
@EnableAutoConfiguration
@ContextConfiguration(
    classes = [PostgresTestContainersConfiguration::class, PostgresOrderRepository::class, PostgresCourierRepository::class]
)
@DisplayName("PostgresOrderRepository should")
class PostgresOrderRepositoryTest {

    @Autowired
    private lateinit var orderRepository: PostgresOrderRepository

    @Autowired
    private lateinit var courierRepository: CourierRepository

    private val courier = Courier("peter", "tram", 1, Location.random())

    @BeforeEach
    fun setUp() {
        courierRepository.add(courier)
    }

    @Test
    @DisplayName("add order and find it by id")
    fun addOrder() {
        val givenOrder = Order(UUID.randomUUID(), Location.random())
        givenOrder.assignTo(courier)

        orderRepository.add(givenOrder)
        val saved = orderRepository.findById(givenOrder.id)

        checkNotNull(saved)
        assertThat(saved).satisfies(
            { assertThat(it.id).isEqualTo(givenOrder.id) },
            { assertThat(it.status).isEqualTo(givenOrder.status) },
            { assertThat(it.courierId).isEqualTo(givenOrder.courierId) },
            { assertThat(it.location).isEqualTo(givenOrder.location) },
        )
    }

    @Test
    @DisplayName("update order")
    fun updateOrder() {
        val givenOrder = Order(UUID.randomUUID(), Location.random())
        orderRepository.add(givenOrder)

        givenOrder.assignTo(courier)
        orderRepository.update(givenOrder)

        val saved = orderRepository.findById(givenOrder.id)

        checkNotNull(saved)
        assertThat(saved).satisfies(
            { assertThat(it.status).isEqualTo(givenOrder.status) },
            { assertThat(it.courierId).isEqualTo(courier.id) },
        )
    }

    @Test
    @DisplayName("find first not dispatched order")
    fun findFirstNotDispatched() {
        val notDispatched1 = Order(UUID.randomUUID(), Location.random())
        val notDispatched2 = Order(UUID.randomUUID(), Location.random())
        val dispatched = Order(UUID.randomUUID(), Location.random()).apply { assignTo(courier) }

        with(orderRepository) {
            add(dispatched)
            add(notDispatched1)
            add(notDispatched2)
        }

        val found = orderRepository.findFirstNotDispatched()

        assertThat(found).isEqualTo(notDispatched1)
    }

    @Test
    @DisplayName("return null when trying to find first not dispatched among dispatched orders")
    fun findFirstNotDispatchedAmongAllDispatchedOrders() {
        val dispatched = Order(UUID.randomUUID(), Location.random()).apply { assignTo(courier) }
        orderRepository.add(dispatched)

        val found = orderRepository.findFirstNotDispatched()

        assertThat(found).isNull()
    }

    @Test
    @DisplayName("find all orders")
    fun findAll() {
        val givenOrders = listOf(
            Order(UUID.randomUUID(), Location.random()),
            Order(UUID.randomUUID(), Location.random())
        )
        givenOrders.forEach { orderRepository.add(it) }


        val found = orderRepository.findAll()

        assertThat(found).containsExactlyInAnyOrderElementsOf(givenOrders)
    }

    @Test
    @DisplayName("find all assigned orders")
    fun findAllAssignedOrders() {
        val vlad = Courier("vlad", "bus", 2, Location.random())
        courierRepository.add(vlad)

        val assignedOrders = listOf(
            Order(UUID.randomUUID(), Location.random()).apply { assignTo(courier) },
            Order(UUID.randomUUID(), Location.random()).apply { assignTo(vlad) }
        )
        assignedOrders.forEach { orderRepository.add(it) }
        orderRepository.add(Order(UUID.randomUUID(), Location.random()))

        val found = orderRepository.findAllAssigned()

        assertThat(found).containsExactlyInAnyOrderElementsOf(assignedOrders)
    }

}