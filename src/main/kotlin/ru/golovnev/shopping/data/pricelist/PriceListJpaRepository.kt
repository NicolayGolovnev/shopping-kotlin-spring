package ru.golovnev.shopping.data.pricelist

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PriceListJpaRepository : JpaRepository<PriceListEntity, UUID>