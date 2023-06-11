package com.showmeyourcode.cqrs.demo.domain.command

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(collection = "products")
data class ProductCommand(
    @Id
    val id: UUID = UUID.randomUUID(), // : ObjectId = ObjectId.get(),
    val name: String,
    var availability: Int,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    var modifiedDate: LocalDateTime = LocalDateTime.now()
)
