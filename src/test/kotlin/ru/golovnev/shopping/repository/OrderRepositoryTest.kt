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
import ru.golovnev.shopping.data.client.ClientJpaRepository
import ru.golovnev.shopping.data.manufacturer.ManufacturerJpaRepository
import ru.golovnev.shopping.data.model.ModelJpaRepository
import ru.golovnev.shopping.data.order.OrderEntity
import ru.golovnev.shopping.data.order.OrderJpaRepository
import ru.golovnev.shopping.data.pricelist.PriceListJpaRepository
import ru.golovnev.shopping.data.product.ProductJpaRepository
import ru.golovnev.shopping.factory.OrderMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class OrderRepositoryTest {
    @Autowired
    lateinit var orderJpaRepository: OrderJpaRepository
    @Autowired
    lateinit var clientJpaRepository: ClientJpaRepository
    @Autowired
    lateinit var priceListJpaRepository: PriceListJpaRepository
    @Autowired
    lateinit var manufacturerJpaRepository: ManufacturerJpaRepository
    @Autowired
    lateinit var modelJpaRepository: ModelJpaRepository
    @Autowired
    lateinit var productJpaRepository: ProductJpaRepository

    private lateinit var firstOrder: OrderEntity
    private lateinit var secondOrder: OrderEntity

    @BeforeEach
    private fun setup() {
        firstOrder = OrderMockFactory.createOrder(
            count = 1,
            clientName = "FirstOrderClientTestName"
        )
        secondOrder = OrderMockFactory.createOrder(
            count = 1,
            clientName = "SecondOrderClientTestName"
        )

        productJpaRepository.save(firstOrder.priceList.product)
        manufacturerJpaRepository.save(firstOrder.priceList.manufacturer)
        modelJpaRepository.save(firstOrder.priceList.model)
        priceListJpaRepository.save(firstOrder.priceList)
        clientJpaRepository.save(firstOrder.client)

        productJpaRepository.save(secondOrder.priceList.product)
        manufacturerJpaRepository.save(secondOrder.priceList.manufacturer)
        modelJpaRepository.save(secondOrder.priceList.model)
        priceListJpaRepository.save(secondOrder.priceList)
        clientJpaRepository.save(secondOrder.client)
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { orderJpaRepository.save(firstOrder) }
        val foundOrder: OrderEntity = orderJpaRepository.findById(firstOrder.id).get()

        assertEquals(foundOrder.count, firstOrder.count)
        assertEquals(foundOrder.client.id, firstOrder.client.id)
        assertEquals(foundOrder.priceList.id, firstOrder.priceList.id)
        assertEquals(foundOrder.priceList.manufacturer.id, firstOrder.priceList.manufacturer.id)
        assertEquals(foundOrder.priceList.model.id, firstOrder.priceList.model.id)
        assertEquals(foundOrder.priceList.product.id, firstOrder.priceList.product.id)
    }

    @Test
    fun `delete by id`() {
        orderJpaRepository.save(firstOrder)

        assertDoesNotThrow { orderJpaRepository.deleteById(firstOrder.id) }
    }

    @Test
    fun `throw when delete another id`() {
        val primaryId: UUID = firstOrder.id
        val anotherId: UUID = UUID.randomUUID()
        orderJpaRepository.save(firstOrder)
        every { firstOrder.id } returns anotherId

        assertNotEquals(primaryId, anotherId)
        assertThrows<EmptyResultDataAccessException> { orderJpaRepository.deleteById(firstOrder.id) }
    }

    @Test
    fun `find all`() {
        orderJpaRepository.deleteAll()
        orderJpaRepository.save(firstOrder)
        orderJpaRepository.save(secondOrder)
        val actualOrders: List<OrderEntity> = listOf(firstOrder, secondOrder)

        var orders: List<OrderEntity> = listOf()
        assertDoesNotThrow { orders = orderJpaRepository.findAll() }
        assertEquals(orders.size, actualOrders.size)
        for ((index, order) in orders.withIndex())
            assertEquals(order.id, actualOrders[index].id)
    }

    @Test
    fun `find by id`() {
        orderJpaRepository.save(firstOrder)
        var foundOrder: OrderEntity? = null

        assertDoesNotThrow { foundOrder = orderJpaRepository.findById(firstOrder.id).get() }
        assertNotNull(foundOrder)

        assertEquals(foundOrder?.count, firstOrder.count)
        assertEquals(foundOrder?.client?.id, firstOrder.client.id)
        assertEquals(foundOrder?.priceList?.id, firstOrder.priceList.id)
        assertEquals(foundOrder?.priceList?.manufacturer?.id, firstOrder.priceList.manufacturer.id)
        assertEquals(foundOrder?.priceList?.model?.id, firstOrder.priceList.model.id)
        assertEquals(foundOrder?.priceList?.product?.id, firstOrder.priceList.product.id)
    }

    @Test
    fun `throw when find by another id`() {
        val actualId: UUID = firstOrder.id
        val anotherId: UUID = UUID.randomUUID()
        orderJpaRepository.save(firstOrder)
        every { firstOrder.id } returns anotherId

        assertNotEquals(actualId, anotherId)
        assertThrows<NoSuchElementException> { orderJpaRepository.findById(firstOrder.id).get() }
    }

    @Test
    fun `update entity`() {
        val primaryCount: Long? = firstOrder.count
        val changedCount: Long = 100
        orderJpaRepository.save(firstOrder)
        every { firstOrder.count } returns changedCount
        orderJpaRepository.save(firstOrder)
        val foundOrder: OrderEntity = orderJpaRepository.findById(firstOrder.id).get()

        assertNotEquals(primaryCount, changedCount)
        assertEquals(firstOrder.count, foundOrder.count)
    }
}