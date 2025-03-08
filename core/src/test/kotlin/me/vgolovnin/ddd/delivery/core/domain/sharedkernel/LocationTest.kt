package me.vgolovnin.ddd.delivery.core.domain.sharedkernel

import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location.Companion.distanceBetween
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@DisplayName("Location should")
class LocationTest {

    @ParameterizedTest
    @CsvSource(
        value = [
            "0, 1",
            "1, 0",
            "11, 10",
            "10, 11"
        ]
    )
    fun `throw exception when created with incorrect coordinates`(x: Int, y: Int) {
        assertThrows(IllegalArgumentException::class.java) {
            Location(x, y)
        }
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "1, 1",
            "10, 10"
        ]
    )
    fun `be created successfully with coordinates within bounds`(x: Int, y: Int) {
        assertDoesNotThrow { Location(x, y) }
    }

    @Test
    fun `be equal to another location with the same coordinates`() {
        assertEquals(Location(3, 4), Location(3, 4))
    }

    @Test
    fun `not be equal to another location with different coordinates`() {
        assertNotEquals(Location(3, 4), Location(5, 4))
        assertNotEquals(Location(3, 4), Location(3, 6))
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "4, 9, 5",
            "2, 6, 0",
            "1, 6, 1",
            "2, 5, 1",
            "1, 3, 4"
        ]
    )
    fun `calculate distance correctly`(targetX: Int, targetY: Int, expectedDistance: Int) {
        assertEquals(expectedDistance, distanceBetween(Location(2, 6), Location(targetX, targetY)))
    }

    @RepeatedTest(10)
    fun `create random location`() {
        assertDoesNotThrow { Location.random() }
    }
}