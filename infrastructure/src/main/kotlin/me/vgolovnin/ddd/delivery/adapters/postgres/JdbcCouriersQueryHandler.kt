package me.vgolovnin.ddd.delivery.adapters.postgres

import dev.ceviz.query.QueryHandler
import me.vgolovnin.ddd.delivery.core.application.usecases.query.AllCouriersQuery
import me.vgolovnin.ddd.delivery.core.application.usecases.query.Courier
import me.vgolovnin.ddd.delivery.core.application.usecases.query.CouriersResponse
import me.vgolovnin.ddd.delivery.core.application.usecases.query.Location
import org.springframework.stereotype.Component

@Component
internal class JdbcCouriersQueryHandler(
    private val courierRepository: JdbcCourierRepository
) : QueryHandler<AllCouriersQuery, CouriersResponse> {

    override suspend fun handle(query: AllCouriersQuery): CouriersResponse =
        courierRepository.findAll().map(::toView)
            .let { CouriersResponse(it) }

    private fun toView(courierRecord: CourierRecord): Courier = with(courierRecord) {
        Courier(id, name, Location(locationX, locationY), transportName)
    }
}