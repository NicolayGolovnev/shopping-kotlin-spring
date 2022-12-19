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
import ru.golovnev.shopping.app.product.ProductService
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.data.product.ProductJpaRepository
import ru.golovnev.shopping.domain.pricelist.PriceList
import ru.golovnev.shopping.domain.product.Product
import ru.golovnev.shopping.factory.PriceListMockFactory
import ru.golovnev.shopping.factory.ProductMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ProductServiceTest {
    @InjectMockKs
    lateinit var productService: ProductService

    @MockK
    lateinit var productJpaRepository: ProductJpaRepository

    @Test
    fun `find by id`() {
        val product = ProductMockFactory.createProduct()
        val id: UUID = product.id

        every { productJpaRepository.findByIdOrNull(id) } returns product

        assertDoesNotThrow { productService.findById(id) }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { productJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { productService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val product: Product = ProductEntity(name = "product")
        every { productJpaRepository.save(product as ProductEntity) } returns ProductMockFactory.createProduct()

        assertDoesNotThrow { productService.save(product) }
    }

    @Test
    fun `find all`() {
        every { productJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { productService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { productService.deleteById(id) } returns Unit

        assertDoesNotThrow { productService.deleteById(id) }
    }
}