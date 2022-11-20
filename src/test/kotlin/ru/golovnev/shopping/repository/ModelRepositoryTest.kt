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
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.model.ModelJpaRepository
import ru.golovnev.shopping.factory.ModelMockFactory
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@SpringBootTest
@ExtendWith(MockKExtension::class)
@Transactional
class ModelRepositoryTest {
    @Autowired
    lateinit var modelJpaRepository: ModelJpaRepository
    @Autowired
    lateinit var manufacturerJpaRepository: ManufacturerJpaRepository

    private lateinit var firstModel: ModelEntity
    private lateinit var secondModel: ModelEntity

    @BeforeEach
    private fun setup() {
        firstModel = ModelMockFactory.createModel(
            name = "FirstTestModel",
            manufacturerName = "FirstTestModelManufacturer"
        )
        secondModel = ModelMockFactory.createModel(
            name = "SecondTestModel",
            manufacturerName = "SecondTestModelManufacturer"
        )

        manufacturerJpaRepository.save(firstModel.manufacturer)
        manufacturerJpaRepository.save(secondModel.manufacturer)
    }

    @Test
    fun `save entity`() {
        assertDoesNotThrow { modelJpaRepository.save(firstModel) }
        val foundModel: ModelEntity = modelJpaRepository.findById(firstModel.id).get()

        assertEquals(firstModel.id, foundModel.id)
        assertEquals(firstModel.name, foundModel.name)
        assertEquals(firstModel.manufacturer.id, foundModel.manufacturer.id)
        assertEquals(firstModel.manufacturer.name, foundModel.manufacturer.name)
        assertEquals(firstModel.priceList.size, foundModel.priceList.size)
    }

    @Test
    fun `delete by id`() {
        modelJpaRepository.save(firstModel)

        assertDoesNotThrow { modelJpaRepository.deleteById(firstModel.id) }
    }

    @Test
    fun `throw when delete by another id`() {
        val primaryId: UUID = firstModel.id
        val anotherId: UUID = UUID.randomUUID()
        modelJpaRepository.save(firstModel)
        every { firstModel.id } returns anotherId

        assertNotEquals(firstModel.id, primaryId)
        assertEquals(firstModel.id, anotherId)
        assertThrows<EmptyResultDataAccessException> { modelJpaRepository.deleteById(firstModel.id) }
    }

    @Test
    fun `find all`() {
        modelJpaRepository.deleteAll()
        modelJpaRepository.save(firstModel)
        modelJpaRepository.save(secondModel)

        val actualModels = listOf(firstModel, secondModel)
        val expectedCountModels = 2
        var models: List<ModelEntity> = listOf()

        assertDoesNotThrow { models = modelJpaRepository.findAll() }
        assertEquals(expectedCountModels, models.size)
        for ((index, model) in models.withIndex()) {
            assertEquals(model.id, actualModels[index].id)
            assertEquals(model.name, actualModels[index].name)
            assertEquals(model.manufacturer.id, actualModels[index].manufacturer.id)
            assertEquals(model.manufacturer.name, actualModels[index].manufacturer.name)
            assertEquals(model.priceList.size, actualModels[index].priceList.size)
        }
    }

    @Test
    fun `find by id`() {
        modelJpaRepository.save(firstModel)

        var foundModel: ModelEntity? = null
        assertDoesNotThrow { foundModel = modelJpaRepository.findById(firstModel.id).get() }
        assertEquals(firstModel.id, foundModel?.id)
    }

    @Test
    fun `throw when find by another id`() {
        val primaryId: UUID = firstModel.id
        val anotherId: UUID = UUID.randomUUID()

        modelJpaRepository.save(firstModel)

        every { firstModel.id } returns anotherId
        assertThrows<NoSuchElementException> { modelJpaRepository.findById(firstModel.id).get() }
    }

    @Test
    fun `update entity`() {
        val changedName: String = "ChangedNameTest"
        manufacturerJpaRepository.save(firstModel.manufacturer)
        modelJpaRepository.save(firstModel)
        every { firstModel.name } returns changedName

        assertDoesNotThrow { modelJpaRepository.save(firstModel) }
        val model: ModelEntity = modelJpaRepository.findById(firstModel.id).get()
        assertEquals(model.name, firstModel.name)
    }
}