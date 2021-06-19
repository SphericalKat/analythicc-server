package cc.analythi.models.response

import java.util.*

data class SiteResponse(
    val id: UUID,
    val name: String,
    val events: List<EventResponse>? = listOf(),
)
