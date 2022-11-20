package ru.golovnev.shopping.domain.model

import ru.golovnev.shopping.domain.pricelist.PriceList
import ru.golovnev.shopping.domain.manufacturer.Manufacturer
import java.util.*

/** Модель товара */
interface Model {
    /** УИД модели */
    val id: UUID

    /** Наименование */
    val name: String

    /** Производитель */
    val manufacturer: Manufacturer

    /** Список товаров для заказа */
    val priceList: List<PriceList>
}