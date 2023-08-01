package com.showmeyourcode.cqrseventsourcing.demo.domain

import com.showmeyourcode.cqrseventsourcing.demo.command.addproduct.AddProductCommand
import com.showmeyourcode.cqrseventsourcing.demo.command.changeavailability.ChangeProductAvailabilityCommand
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.DomainEntity
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.Event
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.ProductCreatedEvent
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.ProductUpdatedEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.Assert
import java.util.*

typealias ProductID = UUID

/**
 * The product domain entity.
 *
 * This entity encapsulates all information which belongs to a product.
 * It also provides business methods to work on this information. Data
 * cannot be changed from outside - there are no setters.
 *
 * Whenever data has been changed a domain event will thrown. This event
 * informs any listener that something has changed in the context of a
 * product. In a real life example, those events would be published over
 * a message broker such as Kafka, ActiveMQ or AWS Kinesis.
 */
class Product : DomainEntity<ProductID>() {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    var productInformation: ProductInformation? = null
        private set

    fun applyAll(vararg events: Event): Product {
        return applyAll(events.toList())
    }

    fun applyAll(events: List<Event>): Product {
        events.forEach {
            when (it) {
                is ProductCreatedEvent -> apply(it)
                is ProductUpdatedEvent -> apply(it)
            }
        }

        return this
    }

    fun handle(command: AddProductCommand): UUID {
        Assert.hasText(command.name, "Product name must not be empty!")
        Assert.notNull(command.availability, "Product availability must not be empty!")
        val id = UUID.randomUUID()

        raise(
            ProductCreatedEvent(
                productID = id,
                name = command.name,
                availability = command.availability,
            ),
        )
        log.info("New product created. [productID={}]", id)
        return id
    }

    fun apply(event: ProductCreatedEvent) {
        id = event.productID
        productInformation = ProductInformation(
            name = event.name,
            availability = event.availability,
        )
    }

    fun handle(command: ChangeProductAvailabilityCommand) {
        Assert.notNull(command.newAvailability, "Product availability must not be empty!")

        raise(
            ProductUpdatedEvent(
                productID = id!!,
                availability = command.newAvailability,
            ),
        )
        log.info("Product data updated. [productID={}]", id)
    }

    fun apply(event: ProductUpdatedEvent) {
        this.productInformation = ProductInformation(
            name = productInformation!!.name,
            availability = event.availability,
        )
    }
}
