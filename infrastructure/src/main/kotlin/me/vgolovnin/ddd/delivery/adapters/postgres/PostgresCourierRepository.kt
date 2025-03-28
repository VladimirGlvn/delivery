package me.vgolovnin.ddd.delivery.adapters.postgres

import me.vgolovnin.ddd.delivery.core.domain.model.courier.Courier
import me.vgolovnin.ddd.delivery.core.domain.sharedkernel.Location
import me.vgolovnin.ddd.delivery.core.ports.CourierRepository
import java.util.*
import kotlin.jvm.optionals.getOrNull

internal class PostgresCourierRepository(
    private val jdbcRepository: JdbcCourierRepository
) : CourierRepository {

    override fun add(courier: Courier): Unit = with(courier) {
        jdbcRepository.save(
            mapToRecord().also { it.new = true }
        )
    }

    private fun Courier.mapToRecord() = CourierRecord(
        id,
        name,
        location.x,
        location.y,
        isFree,
        transport.name,
        transport.speed,
    )

    override fun update(courier: Courier) {
        jdbcRepository.save(courier.mapToRecord())
    }

    override fun findById(id: UUID): Courier? = jdbcRepository.findById(id).map(::toEntity).getOrNull()

    override fun findAllFree(): Collection<Courier> = jdbcRepository.findAllByFree(true).map(::toEntity)

    private fun toEntity(it: CourierRecord) = Courier(
        it.name,
        it.transportName,
        it.transportSpeed,
        Location(it.locationX, it.locationY),
        it.free,
        it.id
    )
}