package ru.golovnev.shopping.web.model

import org.hibernate.Hibernate
import ru.golovnev.shopping.data.manufacturer.ManufacturerEntity
import ru.golovnev.shopping.data.model.ModelEntity
import ru.golovnev.shopping.domain.model.Model
import ru.golovnev.shopping.web.manufacturer.ManufacturerMapper.toDto
import ru.golovnev.shopping.web.manufacturer.ManufacturerMapper.toEntity
import java.util.*

object ModelMapper {
    fun Model.toDto(): ModelDto =
        ModelDto(
            id = this.id,
            name = this.name,
            manufacturer = this.manufacturer.takeIf { Hibernate.isInitialized(it) } ?.toDto()
        )

    fun List<Model>.toDto(): List<ModelDto> =
        this.map { it.toDto() }

    fun ModelDto.toEntity(): ModelEntity =
        ModelEntity(
            id = this.id ?: UUID.randomUUID(),
            name = this.name ?: throw UninitializedPropertyAccessException(
                message = "Необходимо указать название модели"
            ),
            manufacturer = this.manufacturer?.toEntity()
                ?: throw UninitializedPropertyAccessException(
                    message = "Необходимо указать одно из изготовителей"
                )
        )
}