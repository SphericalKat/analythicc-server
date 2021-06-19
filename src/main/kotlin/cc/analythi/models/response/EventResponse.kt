package cc.analythi.models.response

import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.validate
import java.time.LocalDateTime
import java.util.*

data class EventResponse(
    val id: UUID,
    val type: String,
    val time: LocalDateTime,
    val page: String,
    val referer: String,
    val browser: String,
    val location: String,
    val device: String
) {
    fun validate() {
        validate(this) {
            validate(EventResponse::type).isNotBlank().isNotNull()
            validate(EventResponse::time).isNotNull()
            validate(EventResponse::page).isNotBlank().isNotNull()
            validate(EventResponse::referer).isNotBlank().isNotNull()
            validate(EventResponse::browser).isNotBlank().isNotNull()
            validate(EventResponse::location).isNotBlank().isNotNull()
            validate(EventResponse::device).isNotBlank().isNotNull()
        }
    }

    init {
        validate()
    }
}
