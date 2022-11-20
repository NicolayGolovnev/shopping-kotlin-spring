package ru.golovnev.shopping.factory

import io.mockk.every
import io.mockk.mockk
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import java.util.*

object ManufacturerMockFactory {
    fun createManufacturer(
        name: String = "SomeTestName",
        country: String = "SomeTestCountry",
        site: String? = null,
        relaxed: Boolean = true
    ): ManufacturerEntity =
        mockk(relaxed = relaxed) manufacturer@{
            every { this@manufacturer.id } returns UUID.randomUUID()
            every { this@manufacturer.name } returns name
            every { this@manufacturer.country } returns country
            every { this@manufacturer.site } returns site
            every { this@manufacturer.priceList } returns listOf()
            every { this@manufacturer.models } returns listOf()
        }
}