package ru.golovnev.shopping.factory

import io.mockk.every
import io.mockk.mockk
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.product.ProductEntity
import java.util.*

object PriceListMockFactory {
    fun createPriceList(
        price: Long = 0,
        relaxed: Boolean = true
    ): PriceListEntity {
        val manufacturer: ManufacturerEntity = ManufacturerMockFactory.createManufacturer()
        val model: ModelEntity = ModelMockFactory.createModel().also {
            every { it.manufacturer } returns manufacturer
        }

        return mockk(relaxed = relaxed) priceList@{
            every { this@priceList.id } returns UUID.randomUUID()
            every { this@priceList.orders } returns listOf()
            every { this@priceList.product } returns ProductMockFactory.createProduct()
            every { this@priceList.model } returns model
            every { this@priceList.price } returns price
            every { this@priceList.manufacturer } returns manufacturer
        }
    }

}