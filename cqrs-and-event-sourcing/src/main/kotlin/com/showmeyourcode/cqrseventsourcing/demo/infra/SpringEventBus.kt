package com.showmeyourcode.cqrseventsourcing.demo.infra

import com.showmeyourcode.cqrseventsourcing.demo.domain.event.Event
import com.showmeyourcode.cqrseventsourcing.demo.infra.eventbus.EventBus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventBus(private val publisher: ApplicationEventPublisher) : EventBus {

    override fun send(event: Event) {
        publisher.publishEvent(event)
    }
}
