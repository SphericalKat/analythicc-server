package cc.analythi.models.response

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
)
