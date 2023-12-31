package com.showmeyourcode.cqrseventsourcing.demo.infra.eventbus

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@EventListener
@Async
annotation class ProductEventListener
