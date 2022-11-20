package ru.golovnev.shopping.data.model

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ModelJpaRepository : JpaRepository<ModelEntity, UUID> {
    @EntityGraph(attributePaths = ["manufacturer"])
    override fun findById(id: UUID): Optional<ModelEntity>

    @EntityGraph(attributePaths = ["manufacturer"])
    override fun findAll(): List<ModelEntity>
}