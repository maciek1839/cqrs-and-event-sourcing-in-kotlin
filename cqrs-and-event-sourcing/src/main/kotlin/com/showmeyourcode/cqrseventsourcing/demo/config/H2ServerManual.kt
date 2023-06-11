package com.showmeyourcode.cqrseventsourcing.demo.config

import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class H2ServerManual {
    private var webServer: org.h2.tools.Server? = null
    private var tcpServer: org.h2.tools.Server? = null

    @EventListener(ContextRefreshedEvent::class)
    fun start() {
        webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start()
        tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        tcpServer?.stop()
        webServer?.stop()
    }
}
