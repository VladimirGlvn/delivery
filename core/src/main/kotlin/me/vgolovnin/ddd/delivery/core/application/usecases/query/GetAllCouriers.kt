package me.vgolovnin.ddd.delivery.core.application.usecases.query

import dev.ceviz.query.Query
import java.util.*

object AllCouriersQuery : Query<CouriersResponse>

data class CouriersResponse(val couriers: List<Courier>)

data class Courier(val id: UUID, val name: String, val location: Location, val transport: String)
