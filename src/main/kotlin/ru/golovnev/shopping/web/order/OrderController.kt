package ru.golovnev.shopping.web.order

import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.golovnev.shopping.app.order.OrderService
import ru.golovnev.shopping.web.order.OrderMapper.toDto
import java.util.*

@RestController
@RequestMapping("/api/order")
class OrderController(
    private val orderService: OrderService
) {
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    fun findById(@PathVariable("id") orderId: UUID) =
        orderService.findById(orderId).toDto()

    @GetMapping
    @Transactional(readOnly = true)
    fun findAll(): List<OrderDto> =
        orderService.findAll().toDto()

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") clientId: UUID) =
        orderService.deleteById(clientId)

    @PostMapping
    @Transactional
    fun save(@RequestBody orderDto: OrderDto): OrderDto =
        orderService.save(orderDto).toDto()
}