package ru.golovnev.shopping.app.model

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.app.manufacturer.ManufacturerService
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.model.ModelJpaRepository
import ru.golovnev.shopping.domain.model.Model
import ru.golovnev.shopping.web.model.ModelDto
import ru.golovnev.shopping.web.model.ModelMapper.toEntity
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ModelService(
    private val modelJpaRepository: ModelJpaRepository,
    private val manufacturerService: ManufacturerService
) {
    fun save(modelDto: ModelDto): Model =
        modelDto.id?.let {
            modelJpaRepository.findByIdOrNull(it)?.let { model ->
                saveModel(
                    model.apply {
                        modelDto.name?.let { name -> this.name = name }

                        modelDto.manufacturer?.id?.let {
                            this.manufacturer = manufacturerService.findById(it) as ManufacturerEntity
                        } ?: throw UninitializedPropertyAccessException("Необходимо указать УИД изготовителя")
                    }
                )
            }
        } ?: saveModel(modelDto.toEntity())

    private fun saveModel(model: Model): Model =
        modelJpaRepository.save(model as ModelEntity)

    fun deleteById(modelId: UUID) =
        modelJpaRepository.deleteById(modelId)

    fun findAll(): List<Model> =
        modelJpaRepository.findAll()

    fun findById(modelId: UUID): Model =
        modelJpaRepository.findByIdOrNull(modelId)
            ?: throw EntityNotFoundException("Модель с УИД $modelId не найдена")
}