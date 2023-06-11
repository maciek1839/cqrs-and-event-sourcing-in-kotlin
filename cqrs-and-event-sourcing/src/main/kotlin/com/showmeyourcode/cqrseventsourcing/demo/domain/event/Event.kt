package com.showmeyourcode.cqrseventsourcing.demo.domain.event

interface Event {
    fun getDomainEntityId(): String
}
