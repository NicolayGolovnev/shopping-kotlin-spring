package ru.golovnev.shopping.data.order

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderJpaRepository : JpaRepository<OrderEntity, UUID>