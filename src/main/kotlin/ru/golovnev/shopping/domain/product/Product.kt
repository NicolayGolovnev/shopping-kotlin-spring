package ru.golovnev.shopping.domain.product

import ru.golovnev.shopping.domain.pricelist.PriceList
import java.util.*

/** Товар */
interface Product {
    /** УИД товара */
    val id: UUID

    /** Наименование */
    val name: String

    /** Список товаров для заказа */
    val priceList: List<PriceList>
}