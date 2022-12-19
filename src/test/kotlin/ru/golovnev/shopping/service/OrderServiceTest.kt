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
import ru.golovnev.shopping.app.order.OrderService
import ru.golovnev.shopping.data.client.ClientEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.data.order.OrderJpaRepository
import ru.golovnev.shopping.data.pricelist.PriceListEntity
import ru.golovnev.shopping.data.product.ProductEntity
import ru.golovnev.shopping.domain.order.Order
import ru.golovnev.shopping.factory.OrderMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class OrderServiceTest {
    @InjectMockKs
    lateinit var orderService: OrderService

    @MockK
    lateinit var orderJpaRepository: OrderJpaRepository

    @Test
    fun `find by id`() {
        val order = OrderMockFactory.createOrder()
        val id: UUID = order.id

        every { orderJpaRepository.findByIdOrNull(id) } returns order

        assertDoesNotThrow { orderService.findById(id) }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { orderJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { orderService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val client = ClientEntity(name = "client")
        val product = ProductEntity(name = "product")
        val manufacturer = ManufacturerEntity(name = "name", country = "c")
        val model = ModelEntity(name = "model", manufacturer = manufacturer)
        val priceList = PriceListEntity(price = 1, product = product, model = model, manufacturer = manufacturer)
        val orderEntity: Order = OrderEntity(priceList = priceList, client = client)
        every { orderJpaRepository.save(orderEntity as OrderEntity) } returns OrderMockFactory.createOrder()

        assertDoesNotThrow { orderService.save(orderEntity) }
    }

    @Test
    fun `find all`() {
        every { orderJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { orderService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { orderService.deleteById(id) } returns Unit

        assertDoesNotThrow { orderService.deleteById(id) }
    }
}