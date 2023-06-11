package com.showmeyourcode.cqrseventsourcing.demo.infra.eventbus

import com.showmeyourcode.cqrseventsourcing.demo.domain.event.Event

interface EventBus {
    fun send(event: Event)

    fun sendAll(events: List<Event>) {
        events.forEach(this::send)
    }
}
