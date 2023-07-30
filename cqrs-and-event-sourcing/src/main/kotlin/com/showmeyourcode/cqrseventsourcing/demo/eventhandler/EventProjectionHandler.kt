package com.showmeyourcode.cqrseventsourcing.demo.eventhandler

import com.showmeyourcode.cqrseventsourcing.demo.domain.event.Event
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.ProductCreatedEvent
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.ProductUpdatedEvent
import com.showmeyourcode.cqrseventsourcing.demo.domain.query.ProductQuery
import com.showmeyourcode.cqrseventsourcing.demo.infra.eventbus.ProductEventListener
import com.showmeyourcode.cqrseventsourcing.demo.repository.query.ProductQueryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class EventProjectionHandler(private val repository: ProductQueryRepository) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @ProductEventListener
    fun handleProductEvents(event: Event) {
        log.info("Handling an event - $event")
        when (event) {
            is ProductCreatedEvent -> repository.save(
                ProductQuery(
                    event.productID,
                    event.name,
                    event.availability,
                ),
            )
            is ProductUpdatedEvent -> {
                val existingProduct = repository.getById(event.productID)
                repository.save(
                    ProductQuery(
                        event.productID,
                        existingProduct.name,
                        event.availability,
                    ),
                )
            }
            else -> log.warn("Event not handled! $event")
        }
    }
}
