package ru.golovnev.shopping.repository

import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.transaction.annotation.Transactional
import ru.golovnev.shopping.data.manufacturer.ManufacturerJpaRepository
import ru.golovnev.shopping.data.model.ModelJpaRepository
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.pricelist.PriceListJpaRepository
import ru.golovnev.shopping.data.product.ProductJpaRepository
import ru.golovnev.shopping.factory.PriceListMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class PriceListRepositoryTest {
    @Autowired
    lateinit var priceListJpaRepository: PriceListJpaRepository
    @Autowired
    lateinit var productJpaRepository: ProductJpaRepository
    @Autowired
    lateinit var manufacturerJpaRepository: ManufacturerJpaRepository
    @Autowired
    lateinit var modelJpaRepository: ModelJpaRepository

    private lateinit var firstPriceList: PriceListEntity
    private lateinit var secondPriceList: PriceListEntity

    @BeforeEach
    private fun setup() {
        firstPriceList = PriceListMockFactory.createPriceList(
            price = 1
        )
        secondPriceList = PriceListMockFactory.createPriceList(
            price = 2
        )

        productJpaRepository.save(firstPriceList.product)
        manufacturerJpaRepository.save(firstPriceList.manufacturer)
        modelJpaRepository.save(firstPriceList.model)

        productJpaRepository.save(secondPriceList.product)
        manufacturerJpaRepository.save(secondPriceList.manufacturer)
        modelJpaRepository.save(secondPriceList.model)
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { priceListJpaRepository.save(firstPriceList) }
        val foundPriceList: PriceListEntity = priceListJpaRepository.findById(firstPriceList.id).get()

        assertEquals(firstPriceList.id, foundPriceList.id)
        assertEquals(firstPriceList.price, foundPriceList.price)
        assertEquals(firstPriceList.orders.size, foundPriceList.orders.size)
        assertEquals(firstPriceList.product.id, foundPriceList.product.id)
        assertEquals(firstPriceList.product.name, foundPriceList.product.name)
        assertEquals(firstPriceList.model.id, foundPriceList.model.id)
        assertEquals(firstPriceList.model.name, foundPriceList.model.name)
        assertEquals(firstPriceList.manufacturer.id, foundPriceList.manufacturer.id)
        assertEquals(firstPriceList.manufacturer.name, foundPriceList.manufacturer.name)
    }

    @Test
    fun `delete by id`() {
        priceListJpaRepository.save(firstPriceList)

        assertDoesNotThrow { priceListJpaRepository.deleteById(firstPriceList.id) }
    }

    @Test
    fun `throw when delete another id`() {
        val primaryId: UUID = firstPriceList.id
        val anotherId: UUID = UUID.randomUUID()
        priceListJpaRepository.save(firstPriceList)
        every { firstPriceList.id } returns anotherId

        assertThrows<EmptyResultDataAccessException> { priceListJpaRepository.deleteById(firstPriceList.id) }
    }

    @Test
    fun `find all`() {
        priceListJpaRepository.deleteAll()
        priceListJpaRepository.save(firstPriceList)
        priceListJpaRepository.save(secondPriceList)
        val actualPriceLists: List<PriceListEntity> = listOf(firstPriceList, secondPriceList)

        var priceLists: List<PriceListEntity> = listOf()
        assertDoesNotThrow { priceLists = priceListJpaRepository.findAll() }
        for ((index, priceList) in priceLists.withIndex())
            assertEquals(priceList.id, actualPriceLists[index].id)
    }

    @Test
    fun `find by id`() {
        priceListJpaRepository.save(firstPriceList)
        var foundPriceList: PriceListEntity? = null
        assertDoesNotThrow { foundPriceList = priceListJpaRepository.findById(firstPriceList.id).get() }

        assertEquals(firstPriceList.id, foundPriceList?.id)
        assertEquals(firstPriceList.price, foundPriceList?.price)
        assertEquals(firstPriceList.model.id, foundPriceList?.model?.id)
        assertEquals(firstPriceList.manufacturer.id, foundPriceList?.manufacturer?.id)
    }

    @Test
    fun `throw when find by another id`() {
        priceListJpaRepository.save(firstPriceList)
        val primaryId: UUID = firstPriceList.id
        val anotherId: UUID = UUID.randomUUID()
        every { firstPriceList.id } returns anotherId

        assertThrows<NoSuchElementException> { priceListJpaRepository.findById(firstPriceList.id).get() }
    }

    @Test
    fun `update entity`() {
        val primaryPrice = firstPriceList.price
        val changedPrice: Long = 11
        every { firstPriceList.price } returns changedPrice

        assertDoesNotThrow { priceListJpaRepository.save(firstPriceList) }
        assertNotEquals(primaryPrice, firstPriceList.price)
        assertEquals(changedPrice, firstPriceList.price)
    }
}