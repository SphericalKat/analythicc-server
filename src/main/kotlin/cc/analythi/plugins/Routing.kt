@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package cc.analythi.plugins

import cc.analythi.models.Site
import cc.analythi.models.response.Message
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
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
            val site = call.receive<SiteBody>()
            lateinit var newSite: Site
            transaction {
                newSite = Site.new {
                    name = site.name
                }
            }
            call.respond(SiteBody(name = newSite.name, id = newSite.id.toString()))
        }
        get<SiteId> { siteId ->
            var site: Site? = null
            transaction {
                site = Site.findById(UUID.fromString(siteId.id))
            }

            if (site == null) {
                call.respond(HttpStatusCode.NotFound, Message(msg = "could not find a site with id ${siteId.id}"))
            } else {
                call.respond(HttpStatusCode.Found, SiteBody(name = site!!.name, id = site!!.id.toString()))
            }
        }
    }
}

data class SiteBody(val name: String, val id: String)

@Location("/analytics/sites/{id}")
data class SiteId(val id: String)