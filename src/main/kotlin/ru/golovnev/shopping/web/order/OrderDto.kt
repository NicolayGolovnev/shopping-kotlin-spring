package ru.golovnev.shopping.web.order

import ru.golovnev.shopping.web.client.ClientDto
import ru.golovnev.shopping.web.pricelist.PriceListDto
import java.time.LocalDate
import java.util.*

class OrderDto(
    val id: UUID? = null,
    val date: LocalDate? = null,
    val priceList: PriceListDto? = null,
    val count: Long? = null,
    val client: ClientDto? = null
)