package ru.golovnev.shopping.data.pricelist

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PriceListJpaRepository : JpaRepository<PriceListEntity, UUID> {
    @EntityGraph(
        attributePaths = ["product", "manufacturer", "model"]
    )
    override fun findById(id: UUID): Optional<PriceListEntity>
}