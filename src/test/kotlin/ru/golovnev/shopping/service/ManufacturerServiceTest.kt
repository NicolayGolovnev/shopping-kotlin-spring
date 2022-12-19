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
import ru.golovnev.shopping.app.manufacturer.ManufacturerService
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.manufacturer.ManufacturerJpaRepository
import ru.golovnev.shopping.domain.manufacturer.Manufacturer
import ru.golovnev.shopping.factory.ManufacturerMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ManufacturerServiceTest {
    @InjectMockKs
    lateinit var manufacturerService: ManufacturerService

    @MockK
    lateinit var manufacturerJpaRepository: ManufacturerJpaRepository

    @Test
    fun `find by id`() {
        val manufacturer = ManufacturerMockFactory.createManufacturer()
        val id: UUID = manufacturer.id

        every { manufacturerJpaRepository.findByIdOrNull(id) } returns manufacturer

        assertDoesNotThrow { manufacturerService.findById(id) }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { manufacturerJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { manufacturerService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val manufacturerEntity: Manufacturer = ManufacturerEntity(name = "n", country = "c", site = null)
        every { manufacturerJpaRepository.save(manufacturerEntity as ManufacturerEntity) } returns ManufacturerMockFactory.createManufacturer()

        assertDoesNotThrow { manufacturerService.save(manufacturerEntity) }
    }

    @Test
    fun `find all`() {
        every { manufacturerJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { manufacturerService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { manufacturerService.deleteById(id) } returns Unit

        assertDoesNotThrow { manufacturerService.deleteById(id) }
    }
}