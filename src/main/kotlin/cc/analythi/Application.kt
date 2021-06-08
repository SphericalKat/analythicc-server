package cc.analythi

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import cc.analythi.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureHTTP()
        configureMonitoring()
    }.start(wait = true)
}
