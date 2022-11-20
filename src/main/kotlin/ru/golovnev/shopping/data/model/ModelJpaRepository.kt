package ru.golovnev.shopping.data.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ModelJpaRepository : JpaRepository<ModelEntity, UUID>