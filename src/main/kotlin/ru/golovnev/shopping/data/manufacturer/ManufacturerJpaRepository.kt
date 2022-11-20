package ru.golovnev.shopping.data.manufacturer

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ManufacturerJpaRepository : JpaRepository<ManufacturerEntity, UUID>