package ru.golovnev.shopping.domain.order

import ru.golovnev.shopping.domain.pricelist.PriceList
import ru.golovnev.shopping.domain.client.Client
import java.time.LocalDate
import java.util.*

/** Заказ */
interface Order {
    /** УИД заказа */
    val id: UUID

    /** Дата заказа */
    val date: LocalDate

    /** Прайс-лист товара */
    val priceList: PriceList

    /** Количество товара */
    val count: Long?

    /** Покупатель */
    val client: Client
}