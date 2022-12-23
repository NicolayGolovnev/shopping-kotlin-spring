package ru.golovnev.shopping.web.order

import org.hibernate.Hibernate
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.domain.order.Order
import ru.golovnev.shopping.web.client.ClientMapper.toDto
import ru.golovnev.shopping.web.client.ClientMapper.toEntity
import ru.golovnev.shopping.web.pricelist.PriceListMapper.toDto
import ru.golovnev.shopping.web.pricelist.PriceListMapper.toEntity
import java.time.LocalDate
import java.util.*

object OrderMapper {
    fun Order.toDto(): OrderDto =
        OrderDto(
            id = this.id,
            date = this.date,
            priceList = this.priceList.takeIf { Hibernate.isInitialized(it) } ?.toDto(),
            count = this.count,
            client = this.client.takeIf { Hibernate.isInitialized(it) } ?.toDto()
        )

    fun List<Order>.toDto(): List<OrderDto> =
        this.map { it.toDto() }

    fun OrderDto.toEntity(): OrderEntity =
        OrderEntity(
            id = this.id ?: UUID.randomUUID(),
            date = this.date ?: LocalDate.now(),
            priceList = this.priceList?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать список товаров заказа"
                ),
            count = this.count,
            client = this.client?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо покупателя заказа"
                )
        )
}