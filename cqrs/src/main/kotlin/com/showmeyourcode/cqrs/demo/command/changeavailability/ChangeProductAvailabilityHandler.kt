package com.showmeyourcode.cqrs.demo.command.changeavailability

import com.showmeyourcode.cqrs.demo.domain.command.ProductEvent
import com.showmeyourcode.cqrs.demo.event.EventPublisher
import com.showmeyourcode.cqrs.demo.infra.CommandHandler
import com.showmeyourcode.cqrs.demo.repository.command.ProductCommandRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ChangeProductAvailabilityHandler(
    private val repository: ProductCommandRepository,
    private val publisher: EventPublisher,
) :
    CommandHandler<Unit, ChangeProductAvailabilityCommand> {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun handle(command: ChangeProductAvailabilityCommand) {
        log.info("Handling 'ChangeProductAvailabilityHandler' command...")
        val product = repository.findById(command.id)
        if (product.isPresent) {
            product.get().availability = command.newAvailability
            product.get().modifiedDate = LocalDateTime.now()

            publisher.publish(ProductEvent.ProductAvailabilityChanged(this, product.get()))
        }
    }
}
