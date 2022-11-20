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
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerJpaRepository
import ru.golovnev.shopping.factory.ManufacturerMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ManufacturerRepositoryTest {
    @Autowired
    lateinit var manufacturerJpaRepository: ManufacturerJpaRepository

    private lateinit var firstManufacturer: ManufacturerEntity
    private lateinit var secondManufacturer: ManufacturerEntity

    @BeforeEach
    private fun setup() {
        firstManufacturer = ManufacturerMockFactory.createManufacturer(
            name = "FirstTestManufact",
            country = "FirstTestCountry",
            relaxed = true
        )
        secondManufacturer = ManufacturerMockFactory.createManufacturer(
            name = "SecondTestManufact",
            country = "SecondTestCountry",
            relaxed = true
        )
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { manufacturerJpaRepository.save(firstManufacturer) }
        val findManufacturer = manufacturerJpaRepository.findById(firstManufacturer.id).get()

        assertEquals(firstManufacturer.id, findManufacturer.id)
        assertEquals(firstManufacturer.name, findManufacturer.name)
        assertEquals(firstManufacturer.country, findManufacturer.country)
        assertEquals(firstManufacturer.site, findManufacturer.site)
        assertEquals(firstManufacturer.models.size, findManufacturer.models.size)
        assertEquals(firstManufacturer.priceList.size, findManufacturer.priceList.size)
    }

    @Test
    fun `delete by id`() {
        val id: UUID = secondManufacturer.id
        manufacturerJpaRepository.save(secondManufacturer)
        val countManufacturerBeforeDelete = manufacturerJpaRepository.findAll().size

        assertDoesNotThrow { manufacturerJpaRepository.deleteById(id) }
        assertEquals(manufacturerJpaRepository.findAll().size, countManufacturerBeforeDelete - 1)
    }

    @Test
    fun `throw when delete another id`() {
        val primaryId: UUID = firstManufacturer.id
        val anotherId: UUID = UUID.randomUUID()
        manufacturerJpaRepository.save(firstManufacturer)
        every { firstManufacturer.id } returns anotherId

        assertNotEquals(firstManufacturer.id, primaryId)
        assertEquals(firstManufacturer.id, anotherId)
        assertThrows<EmptyResultDataAccessException> { manufacturerJpaRepository.deleteById(firstManufacturer.id) }
    }

    @Test
    fun `find all entity`() {
        manufacturerJpaRepository.deleteAll()
        manufacturerJpaRepository.save(firstManufacturer)
        manufacturerJpaRepository.save(secondManufacturer)

        var manufacturers: List<ManufacturerEntity> = listOf()
        assertDoesNotThrow { manufacturers = manufacturerJpaRepository.findAll() }

        val actualCountManufacturers = 2
        val expectedCount = manufacturers.size
        assertEquals(expectedCount, actualCountManufacturers)
        val listOfManufacturer = listOf(firstManufacturer, secondManufacturer)
        for ((index, manufacturer) in manufacturers.withIndex()) {
            assertEquals(manufacturer.id, listOfManufacturer[index].id)
            assertEquals(manufacturer.name, listOfManufacturer[index].name)
            assertEquals(manufacturer.country, listOfManufacturer[index].country)
            assertEquals(manufacturer.site, listOfManufacturer[index].site)
        }
    }

    @Test
    fun `find by id`() {
        val id: UUID = firstManufacturer.id
        manufacturerJpaRepository.save(firstManufacturer)
        var foungManufacurer: ManufacturerEntity? = null

        assertDoesNotThrow { foungManufacurer = manufacturerJpaRepository.findById(id).get() }
        assertEquals(foungManufacurer?.id, firstManufacturer.id)
        assertEquals(foungManufacurer?.name, firstManufacturer.name)
        assertEquals(foungManufacurer?.country, firstManufacturer.country)
        assertEquals(foungManufacurer?.site, firstManufacturer.site)
        assertEquals(foungManufacurer?.models?.size, firstManufacturer.models.size)
        assertEquals(foungManufacurer?.priceList?.size, firstManufacturer.priceList.size)
    }

    @Test
    fun `throw when find by another id`() {
        val primaryId: UUID = secondManufacturer.id
        val anotherId: UUID = UUID.randomUUID()
        manufacturerJpaRepository.save(secondManufacturer)
        every { secondManufacturer.id } returns anotherId

        assertNotEquals(secondManufacturer.id, primaryId)
        assertEquals(secondManufacturer.id, anotherId)
        assertThrows<NoSuchElementException> { manufacturerJpaRepository.findById(anotherId).get() }
    }

    @Test
    fun `update entity`() {
        val changedName: String = "ChangedNameTest"
        manufacturerJpaRepository.save(firstManufacturer)
        every { firstManufacturer.name } returns changedName

        var changedManufacturer: ManufacturerEntity? = null
        assertDoesNotThrow { changedManufacturer = manufacturerJpaRepository.save(firstManufacturer) }
        assertEquals(firstManufacturer.id, changedManufacturer?.id)
        assertEquals(firstManufacturer.name, changedManufacturer?.name)
    }
}