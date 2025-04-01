package me.vgolovnin.ddd.delivery.api.adapters.http

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
interface DeliveryApi {

    @Operation(
        tags = ["delivery",],
        summary = "Создать заказ",
        operationId = "createOrder",
        description = """Позволяет создать заказ с целью тестирования""",
        responses = [
            ApiResponse(responseCode = "201", description = "Успешный ответ"),
            ApiResponse(responseCode = "200", description = "Ошибка", content = [Content(schema = Schema(implementation = Error::class))])
        ]
    )
    @RequestMapping(
            method = [RequestMethod.POST],
            value = ["/api/v1/orders"],
            produces = ["application/json"]
    )
    fun createOrder(): ResponseEntity<Unit>

    @Operation(
        tags = ["delivery",],
        summary = "Получить всех курьеров",
        operationId = "getCouriers",
        description = """Позволяет получить всех курьеров""",
        responses = [
            ApiResponse(responseCode = "200", description = "Успешный ответ", content = [Content(array = ArraySchema(schema = Schema(implementation = Courier::class)))]),
            ApiResponse(responseCode = "200", description = "Ошибка", content = [Content(schema = Schema(implementation = Error::class))])
        ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/api/v1/couriers"],
            produces = ["application/json"]
    )
    fun getCouriers(): ResponseEntity<List<Courier>>

    @Operation(
        tags = ["delivery",],
        summary = "Получить все незавершенные заказы",
        operationId = "getOrders",
        description = """Позволяет получить все незавершенные""",
        responses = [
            ApiResponse(responseCode = "200", description = "Успешный ответ", content = [Content(array = ArraySchema(schema = Schema(implementation = Order::class)))]),
            ApiResponse(responseCode = "200", description = "Ошибка", content = [Content(schema = Schema(implementation = Error::class))])
        ]
    )
    @RequestMapping(
            method = [RequestMethod.GET],
            value = ["/api/v1/orders/active"],
            produces = ["application/json"]
    )
    fun getOrders(): ResponseEntity<List<Order>>
}
