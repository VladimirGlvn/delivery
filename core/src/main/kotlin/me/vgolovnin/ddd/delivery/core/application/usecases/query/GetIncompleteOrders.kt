package me.vgolovnin.ddd.delivery.core.application.usecases.query

import dev.ceviz.query.Query
import java.util.*

object IncompleteOrdersQuery : Query<IncompleteOrdersResponse>

data class IncompleteOrdersResponse(val orders: List<Order>)

data class Order(val id: UUID, val location: Location)
