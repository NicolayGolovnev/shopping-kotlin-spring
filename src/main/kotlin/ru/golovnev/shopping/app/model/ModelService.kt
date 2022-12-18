package ru.golovnev.shopping.app.model

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.data.model.ModelJpaRepository
import ru.golovnev.shopping.domain.model.Model
import java.util.*
import javax.persistence.EntityNotFoundException

@Service
class ModelService(
    private val modelJpaRepository: ModelJpaRepository
) {
    fun save(model: Model): Model =
        modelJpaRepository.save(model as ModelEntity)

    fun deleteById(modelId: UUID) =
        modelJpaRepository.deleteById(modelId)

    fun findAll(): List<Model> =
        modelJpaRepository.findAll()

    fun findById(modelId: UUID): Model =
        modelJpaRepository.findByIdOrNull(modelId)
            ?: throw EntityNotFoundException("Модель с УИД $modelId не найдена")
}