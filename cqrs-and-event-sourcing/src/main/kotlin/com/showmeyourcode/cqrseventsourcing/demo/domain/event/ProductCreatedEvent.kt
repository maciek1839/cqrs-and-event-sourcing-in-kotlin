package com.showmeyourcode.cqrseventsourcing.demo.domain.event

import com.showmeyourcode.cqrseventsourcing.demo.domain.ProductID

data class ProductCreatedEvent(
    val productID: ProductID,
    val availability: Int,
    val name: String
) : Event {
    override fun getDomainEntityId(): String {
        return productID.toString()
    }
}
