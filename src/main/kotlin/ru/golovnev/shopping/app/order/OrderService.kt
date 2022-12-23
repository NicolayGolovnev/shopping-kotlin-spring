package ru.golovnev.shopping.app.order

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.app.client.ClientService
import ru.golovnev.shopping.app.pricelist.PriceListService
import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.data.order.OrderJpaRepository
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.domain.order.Order
import ru.golovnev.shopping.web.order.OrderDto
import ru.golovnev.shopping.web.order.OrderMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class OrderService(
    private val orderJpaRepository: OrderJpaRepository,
    private val priceListService: PriceListService,
    private val clientService: ClientService
) {
    fun save(orderDto: OrderDto): Order =
        orderDto.id?.let {
            orderJpaRepository.findByIdOrNull(it)?.let { order ->
                saveOrder(
                    order.apply {
                        orderDto.date?.let { date -> this.date = date }

                        orderDto.priceList?.id?.let {
                            this.priceList = priceListService.findById(it) as PriceListEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД списка товаров")

                        this.count = orderDto.count

                        orderDto.client?.id?.let {
                            this.client = clientService.findById(it) as ClientEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД клиента")
                    }
                )
            }
        } ?: saveOrder(orderDto.toEntity())

    private fun saveOrder(order: Order): Order =
        orderJpaRepository.save(order as OrderEntity)

    fun deleteById(orderId: UUID) =
        orderJpaRepository.deleteById(orderId)

    fun findAll(): List<Order> =
        orderJpaRepository.findAll()

    fun findById(orderId: UUID): Order =
        orderJpaRepository.findByIdOrNull(orderId)
            ?: throw EntityNotFoundException("Заказ с УИД $orderId not found")
}