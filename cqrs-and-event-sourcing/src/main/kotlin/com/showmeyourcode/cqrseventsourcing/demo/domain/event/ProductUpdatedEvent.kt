package com.showmeyourcode.cqrseventsourcing.demo.domain.event

import com.showmeyourcode.cqrseventsourcing.demo.domain.ProductID

data class ProductUpdatedEvent(
    val productID: ProductID,
    val availability: Int,
) : Event {
    override fun getDomainEntityId(): String {
        return productID.toString()
    }
}
