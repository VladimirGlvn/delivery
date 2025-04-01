package me.vgolovnin.ddd.delivery.core.application.usecases.query

import java.util.*

object IncompleteOrdersQuery

data class IncompleteOrdersResponse(val orders: List<Order>)

data class Order(val id: UUID, val location: Location)

interface IncompleteOrderQueryHandler {
    fun handle(query: IncompleteOrdersQuery): IncompleteOrdersResponse
}