package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.test.context.ContextConfiguration

@DataJdbcTest
@EnableAutoConfiguration
@ContextConfiguration(classes = [PostgresCourierRepository::class, PostgresTestContainersConfiguration::class])
@DisplayName("PostgresCourierRepository should")
class PostgresCourierRepositoryTest {

    @Autowired
    private lateinit var repository: PostgresCourierRepository

    @Test
    @DisplayName("add courier and find it by id")
    fun add() {
        val givenCourier = Courier("ivan", "car", 2, Location.random())

        repository.add(givenCourier)
        val saved = repository.findById(givenCourier.id)

        checkNotNull(saved)
        assertThat(saved).satisfies(
            { assertThat(it.id).isEqualTo(givenCourier.id) },
            { assertThat(it.name).isEqualTo(givenCourier.name) },
            { assertThat(it.transport.speed).isEqualTo(givenCourier.transport.speed) },
            { assertThat(it.transport.name).isEqualTo(givenCourier.transport.name) },
            { assertThat(it.isFree).isEqualTo(givenCourier.isFree) },
            { assertThat(it.location).isEqualTo(givenCourier.location) },
        )
    }

    @Test
    @DisplayName("update courier")
    fun update() {
        val givenCourier = Courier("vladimir", "bike", 3, Location(3, 4))

        repository.add(givenCourier)
        givenCourier.apply {
            moveTo(Location(3, 5))
            isFree = false
        }
        repository.update(givenCourier)

        val saved = repository.findById(givenCourier.id)

        checkNotNull(saved)
        assertThat(saved).satisfies(
            { assertThat(it.id).isEqualTo(givenCourier.id) },
            { assertThat(it.name).isEqualTo(givenCourier.name) },
            { assertThat(it.transport.speed).isEqualTo(givenCourier.transport.speed) },
            { assertThat(it.transport.name).isEqualTo(givenCourier.transport.name) },
            { assertThat(it.isFree).isEqualTo(givenCourier.isFree) },
            { assertThat(it.location).isEqualTo(givenCourier.location) },
        )
    }

    @Test
    @DisplayName("find all free couriers")
    fun findAllFree() {
        val freeCourier1 = Courier("vladimir", "bike", 3, Location.random())
        val freeCourier2 = Courier("mike", "feet", 1, Location.random())
        val busyCourier = Courier("alex", "car", 2, Location.random(), isFree = false)

        repository.add(freeCourier1)
        repository.add(busyCourier)
        repository.add(freeCourier2)

        val result = repository.findAllFree()

        assertThat(result).containsExactlyInAnyOrder(freeCourier1, freeCourier2)
    }

}