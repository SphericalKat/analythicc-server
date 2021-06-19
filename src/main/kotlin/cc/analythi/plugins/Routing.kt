@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package cc.analythi.plugins

import cc.analythi.models.AnalyticsEvent
import cc.analythi.models.Site
import cc.analythi.models.response.Message
import cc.analythi.models.response.SiteResponse
import io.ktor.application.*
import io.ktor.http.*
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
            lateinit var newSite: Site
            transaction {
                newSite = Site.new {
                    name = site.name
                }
            }
            call.respond(newSite.toModel())
        }
        get<SiteId> { siteId ->
            var response: SiteResponse? = null
            transaction {
                response = Site.findById(UUID.fromString(siteId.id))
                    ?.load(Site::events)
                    ?.toModel()
            }

            if (response == null) {
                call.respond(HttpStatusCode.NotFound, Message(msg = "could not find a site with id ${siteId.id}"))
            } else {
                call.respond(response!!)
            }
        }
    }
}


@Location("/analytics/sites/{id}")
data class SiteId(val id: String)