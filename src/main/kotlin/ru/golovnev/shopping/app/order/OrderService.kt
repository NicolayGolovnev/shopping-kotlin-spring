package ru.golovnev.shopping.app.order

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.data.order.OrderJpaRepository
import ru.golovnev.shopping.domain.order.Order
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class OrderService(
    private val orderJpaRepository: OrderJpaRepository
) {
    fun save(order: Order): Order =
        orderJpaRepository.save(order as OrderEntity)

    fun deleteById(orderId: UUID) =
        orderJpaRepository.deleteById(orderId)

    fun findAll(): List<Order> =
        orderJpaRepository.findAll()

    fun findById(orderId: UUID): Order =
        orderJpaRepository.findByIdOrNull(orderId)
            ?: throw EntityNotFoundException("Заказ с УИД $orderId not found")
}