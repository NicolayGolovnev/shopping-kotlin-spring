package ru.golovnev.shopping.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional
import ru.golovnev.shopping.app.pricelist.PriceListService
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.pricelist.PriceListJpaRepository
import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.domain.pricelist.PriceList
import ru.golovnev.shopping.factory.PriceListMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class PriceListServiceTest {
    @InjectMockKs
    lateinit var priceListService: PriceListService

    @MockK
    lateinit var priceListJpaRepository: PriceListJpaRepository

    @Test
    fun `find by id`() {
        val priceList = PriceListMockFactory.createPriceList()
        val id: UUID = priceList.id

        every { priceListJpaRepository.findByIdOrNull(id) } returns priceList

        assertDoesNotThrow { priceListService.findById(id) }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { priceListJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { priceListService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val product = ProductEntity(name = "product")
        val manufacturer = ManufacturerEntity(name = "name", country = "c")
        val model = ModelEntity(name = "model", manufacturer = manufacturer)
        val priceListEntity: PriceList = PriceListEntity(price = 1, product = product, model = model, manufacturer = manufacturer)
        every { priceListJpaRepository.save(priceListEntity as PriceListEntity) } returns PriceListMockFactory.createPriceList()

        assertDoesNotThrow { priceListService.save(priceListEntity) }
    }

    @Test
    fun `find all`() {
        every { priceListJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { priceListService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { priceListService.deleteById(id) } returns Unit

        assertDoesNotThrow { priceListService.deleteById(id) }
    }
}