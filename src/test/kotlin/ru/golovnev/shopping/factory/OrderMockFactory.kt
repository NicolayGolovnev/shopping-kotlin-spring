package ru.golovnev.shopping.factory

import io.mockk.every
import io.mockk.mockk
import ru.golovnev.shopping.data.order.OrderEntity
import java.time.LocalDate
import java.util.*

object OrderMockFactory {
    fun createOrder(
        count: Long? = null,
        clientName: String = "ClientTestName",
        relaxed: Boolean = true
    ): OrderEntity =
        mockk(relaxed = relaxed) order@{
            every { this@order.id } returns UUID.randomUUID()
            every { this@order.date } returns LocalDate.now()
            every { this@order.priceList } returns PriceListMockFactory.createPriceList()
            every { this@order.count } returns count
            every { this@order.client } returns ClientMockFactory.createClient(name = clientName)
        }
}