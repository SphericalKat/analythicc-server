package cc.analythi

import cc.analythi.plugins.configureHTTP
import cc.analythi.plugins.configureMonitoring
import cc.analythi.plugins.configureRouting
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
                configureRouting()
                configureHTTP()
                configureMonitoring()
            }.start(wait = true)
        }
    }
}