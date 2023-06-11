package com.showmeyourcode.cqrseventsourcing.demo.repository.eventstore

import com.showmeyourcode.cqrseventsourcing.demo.domain.ProductID
import com.showmeyourcode.cqrseventsourcing.demo.domain.event.Event
import org.springframework.stereotype.Service

@Service
class InMemoryEventStore : EventStore {

    private val events = mutableListOf<Event>()

    override fun save(event: Event) {
        events.add(event)
    }

    override fun allFor(productNumber: ProductID): List<Event> {
        return events.filter {
            it.getDomainEntityId() == productNumber.toString()
        }
    }

    override fun exists(productNumber: ProductID): Boolean {
        return allFor(productNumber).isNotEmpty()
    }

    fun clear() {
        events.clear()
    }
}
