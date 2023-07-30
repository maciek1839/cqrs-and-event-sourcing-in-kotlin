package com.showmeyourcode.cqrseventsourcing.demo.domain.query

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class ProductQuery(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String?,
    val availability: Int = 0,
)
