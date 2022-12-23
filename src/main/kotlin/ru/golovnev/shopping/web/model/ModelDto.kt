package ru.golovnev.shopping.web.model

import ru.golovnev.shopping.web.manufacturer.ManufacturerDto
import java.util.*

class ModelDto(
    val id: UUID? = null,
    val name: String? = null,
    val manufacturer: ManufacturerDto? = null
)