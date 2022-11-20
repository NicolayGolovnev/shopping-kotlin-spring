package ru.golovnev.shopping.factory

import io.mockk.every
import io.mockk.mockk
import ru.golovnev.shopping.data.product.ProductEntity
import java.util.*

object ProductMockFactory {
    fun createProduct(
        name: String = "ProductTestName",
        relaxed: Boolean = true
    ): ProductEntity =
        mockk(relaxed = relaxed) product@{
            every { this@product.id } returns UUID.randomUUID()
            every { this@product.name } returns name
            every { this@product.priceList } returns listOf()
        }
}