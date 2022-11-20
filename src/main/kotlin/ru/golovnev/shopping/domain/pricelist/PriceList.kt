package ru.golovnev.shopping.domain.pricelist

import ru.golovnev.shopping.domain.product.Product
import ru.golovnev.shopping.domain.manufacturer.Manufacturer
import ru.golovnev.shopping.domain.model.Model
import ru.golovnev.shopping.domain.order.Order
import java.util.*

/** Прайс-лист товара */
interface PriceList {
    /** УИД прайс-листа */
    val id: UUID

    /** Заказы */
    val orders: List<Order>

    /** Товар */
    val product: Product

    /** Модель производителя */
    val model: Model

    /** Цена */
    val price: Long

    /** Производитель */
    val manufacturer: Manufacturer
}