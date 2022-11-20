package ru.golovnev.shopping.domain.client

import ru.golovnev.shopping.domain.order.Order
import java.util.*

/** Покупатель */
interface Client {
    /** УИД покупателя */
    val id: UUID

    /** ФИО покупателя */
    val name: String

    /** Телефон */
    val telephone: Long?

    /** Заказы клиента */
    val orders: List<Order>
}