package com.showmeyourcode.cqrseventsourcing.demo.domain.query

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ProductQ(
    @Id
    val id: UUID = UUID.randomUUID(),
    val name: String?,
    val availability: Int = 0
)
