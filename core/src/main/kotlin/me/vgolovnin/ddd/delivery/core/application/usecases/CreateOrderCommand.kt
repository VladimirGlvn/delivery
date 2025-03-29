package me.vgolovnin.ddd.delivery.core.application.usecases

import java.util.*

data class CreateOrderCommand(
    val basketId: UUID,
    val street: String,
)