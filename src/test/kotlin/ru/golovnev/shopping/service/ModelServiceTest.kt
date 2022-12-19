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
import ru.golovnev.shopping.app.model.ModelService
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.model.ModelJpaRepository
import ru.golovnev.shopping.domain.model.Model
import ru.golovnev.shopping.factory.ManufacturerMockFactory
import ru.golovnev.shopping.factory.ModelMockFactory
import java.util.*
import javax.persistence.EntityNotFoundException
import kotlin.test.assertEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ModelServiceTest {
    @InjectMockKs
    lateinit var modelService: ModelService

    @MockK
    lateinit var modelJpaRepository: ModelJpaRepository

    @Test
    fun `find by id`() {
        val model = ModelMockFactory.createModel(name = "test-model")
        val id: UUID = model.id

        every { modelJpaRepository.findByIdOrNull(id) } returns model

        assertDoesNotThrow {
            val findingModel = modelService.findById(id)
            assertEquals(model.name, findingModel.name)
        }
    }

    @Test
    fun `throw when dont find by id`() {
        val id: UUID = UUID.randomUUID()
        every { modelJpaRepository.findByIdOrNull(id) } returns null

        assertThrows<EntityNotFoundException> { modelService.findById(id) }
    }

    @Test
    fun `save entity`() {
        val manufacturer = ManufacturerMockFactory.createManufacturer()
        val modelEntity: Model = ModelEntity(name = "Bip", manufacturer = manufacturer)
        every { modelJpaRepository.save(modelEntity as ModelEntity) } returns ModelMockFactory.createModel(name = "Bip")

        assertDoesNotThrow {
            val model = modelService.save(modelEntity)
            assertEquals(modelEntity.name, model.name)
        }
    }

    @Test
    fun `find all`() {
        every { modelJpaRepository.findAll() } returns listOf()

        assertDoesNotThrow { modelService.findAll() }
    }

    @Test
    fun `delete by id`() {
        val id: UUID = UUID.randomUUID()
        every { modelService.deleteById(id) } returns Unit

        assertDoesNotThrow { modelService.deleteById(id) }
    }
}