package ru.golovnev.shopping.domain.manufacturer

import ru.golovnev.shopping.domain.model.Model
import ru.golovnev.shopping.domain.pricelist.PriceList
import java.util.*

/** Производитель */
interface Manufacturer {
    /** УИД производителя */
    val id: UUID

    /** Наименование */
    val name: String

    /** Страна производителя */
    val country: String

    /** Сайт производителя */
    val site: String?

    /** Список товаров для заказа */
    val priceList: List<PriceList>

    /** Модели производителя */
    val models: List<Model>
}