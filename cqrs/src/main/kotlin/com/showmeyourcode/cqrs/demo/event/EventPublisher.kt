package com.showmeyourcode.cqrs.demo.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisher(private val eventPublisher: ApplicationEventPublisher) {

    fun publish(event: Event) {
        eventPublisher.publishEvent(event)
    }
}
