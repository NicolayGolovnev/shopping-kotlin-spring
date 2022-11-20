package ru.golovnev.shopping.data.product

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductJpaRepository : JpaRepository<ProductEntity, UUID>