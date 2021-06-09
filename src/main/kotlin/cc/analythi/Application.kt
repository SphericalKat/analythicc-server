package cc.analythi

import cc.analythi.models.AnalyticsEvents
import cc.analythi.models.Sites
import cc.analythi.plugins.configureHTTP
import cc.analythi.plugins.configureMonitoring
import cc.analythi.plugins.configureRouting
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

private const val dbUrl = "jdbc:postgresql://localhost:5432/analythicc"
private const val driver = "org.postgresql.Driver"
private const val username = "postgres"
private const val password = "password"

fun main() {
    // initialize db
    Database.connect(dbUrl, driver = driver, user = username, password = password)

    // run db migrations
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Sites, AnalyticsEvents)
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        install(Koin) {
            SLF4JLogger()
        }
        configureRouting()
        configureHTTP()
        configureMonitoring()
    }.start(wait = true)
}
