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
import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.data.product.ProductJpaRepository
import ru.golovnev.shopping.factory.ProductMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ProductRepositoryTest {
    @Autowired
    lateinit var productJpaRepository: ProductJpaRepository

    private lateinit var firstProduct: ProductEntity
    private lateinit var secondProduct: ProductEntity

    @BeforeEach
    private fun setup() {
        firstProduct = ProductMockFactory.createProduct(
            name = "FirstProductTestName"
        )
        secondProduct = ProductMockFactory.createProduct(
            name = "SecondProductTestName"
        )
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { productJpaRepository.save(firstProduct) }
        val foundProduct: ProductEntity = productJpaRepository.findById(firstProduct.id).get()

        assertEquals(firstProduct.id, foundProduct.id)
        assertEquals(firstProduct.name, foundProduct.name)
        assertEquals(firstProduct.priceList.size, foundProduct.priceList.size)

        productJpaRepository.deleteAll()
    }

    @Test
    fun `delete by id`() {
        productJpaRepository.save(firstProduct)
        val countProducts = productJpaRepository.findAll().size

        assertDoesNotThrow { productJpaRepository.deleteById(firstProduct.id) }
        assertEquals(countProducts - 1, productJpaRepository.findAll().size)
    }

    @Test
    fun `throw when delete another id`() {
        productJpaRepository.save(firstProduct)
        val primaryId: UUID = firstProduct.id
        val anotherId: UUID = UUID.randomUUID()
        every { firstProduct.id } returns anotherId

        assertNotEquals(firstProduct.id, primaryId)
        assertThrows<EmptyResultDataAccessException> { productJpaRepository.deleteById(firstProduct.id) }

        productJpaRepository.deleteAll()
    }

    @Test
    @Transactional
    fun `find all`() {
        productJpaRepository.deleteAll()
        productJpaRepository.save(firstProduct)
        productJpaRepository.save(secondProduct)
        val actualProducts = listOf(firstProduct, secondProduct)

        var products: List<ProductEntity> = listOf()
        assertDoesNotThrow { products = productJpaRepository.findAll() }
        assertEquals(actualProducts.size, products.size)
        for ((index, product) in products.withIndex()) {
            assertEquals(product.id, actualProducts[index].id)
            assertEquals(product.name, actualProducts[index].name)
        }

        productJpaRepository.deleteAll()
    }

    @Test
    fun `find by id`() {
        productJpaRepository.save(firstProduct)
        var product: ProductEntity? = null

        assertDoesNotThrow { product = productJpaRepository.findById(firstProduct.id).get() }
        assertEquals(firstProduct.name, product?.name)
    }

    @Test
    fun `throw when find by another id`() {
        val primaryId: UUID = firstProduct.id
        val anotherId: UUID = UUID.randomUUID()
        productJpaRepository.save(firstProduct)
        every { firstProduct.id } returns anotherId

        assertNotEquals(primaryId, anotherId)
        assertThrows<NoSuchElementException> { productJpaRepository.findById(firstProduct.id).get() }
    }

    @Test
    fun `update entity`() {
        productJpaRepository.save(firstProduct)
        val changedName: String = "ChangedNameTest"
        every { firstProduct.name } returns changedName

        assertDoesNotThrow { productJpaRepository.save(firstProduct) }
        val product: ProductEntity = productJpaRepository.findById(firstProduct.id).get()
        assertEquals(product.name, firstProduct.name)
    }
}