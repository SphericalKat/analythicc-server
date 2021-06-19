package cc.analythi.plugins

import cc.analythi.models.response.Message
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureHTTP() {
    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
    install(ContentNegotiation) {
        gson() // parsing json data
    }
    install(StatusPages) {
        // not found exceptions
        exception<NotFoundException> { cause ->
            call.respond(HttpStatusCode.NotFound, Message(error = cause.localizedMessage))
        }

        // general error
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, Message(error = cause.localizedMessage))
        }
    }
}
