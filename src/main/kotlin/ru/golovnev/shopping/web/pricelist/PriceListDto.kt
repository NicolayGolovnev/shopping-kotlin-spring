package ru.golovnev.shopping.web.pricelist

import ru.golovnev.shopping.web.manufacturer.ManufacturerDto
import ru.golovnev.shopping.web.model.ModelDto
import ru.golovnev.shopping.web.product.ProductDto
import java.util.*

class PriceListDto(
    val id: UUID? = null,
    val product: ProductDto? = null,
    val model: ModelDto? = null,
    val price: Long? = null,
    val manufacturer: ManufacturerDto? = null
)