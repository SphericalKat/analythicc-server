@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package cc.analythi.plugins

import cc.analythi.models.Site
import cc.analythi.models.response.SiteResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*


@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureRouting() {
    install(Locations) {}

    routing {
        get("/") {
            call.respondText("OK")
        }
        post("/analytics/sites") {
            val site = call.receive<SiteResponse>()
            lateinit var newSite: SiteResponse
            transaction {
                newSite = Site.new {
                    name = site.name
                }.toModel()
            }
            call.respond(newSite)
        }
        get<SiteId> { siteId ->
            var response: SiteResponse? = null
            transaction {
                response = Site.findById(UUID.fromString(siteId.id))
                    ?.load(Site::events)
                    ?.toModel()
            }

            if (response == null) {
                throw NotFoundException()
            }

            call.respond(response!!)
        }
    }
}


@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/analytics/sites/{id}")
data class SiteId(val id: String)