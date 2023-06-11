package com.showmeyourcode.cqrs.demo.eventhandler

import com.showmeyourcode.cqrs.demo.domain.command.ProductEvent
import com.showmeyourcode.cqrs.demo.domain.query.ProductQuery
import com.showmeyourcode.cqrs.demo.repository.query.ProductQueryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EventProjectionHandler(private val repository: ProductQueryRepository) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @EventListener
    fun handleProductCreatedEvent(event: ProductEvent.ProductCreated) {
        log.info("Handling an event - $event")
        repository.save(ProductQuery(event.newProduct.id, event.newProduct.name, event.newProduct.availability))
    }

    @EventListener
    fun handleProductAvailabilityChangedEvent(event: ProductEvent.ProductAvailabilityChanged) {
        log.info("Handling an event - $event")
        repository.save(ProductQuery(event.newProduct.id, event.newProduct.name, event.newProduct.availability))
    }
}
