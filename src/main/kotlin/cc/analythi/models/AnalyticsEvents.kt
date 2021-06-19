package cc.analythi.models

import cc.analythi.models.response.EventResponse
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.LocalDateTime
import java.util.*

object AnalyticsEvents : UUIDTable("analytics_events", "id") {
    val type: Column<String> = text("type")
    val time: Column<LocalDateTime> = datetime("time")
    val page: Column<String> = text("page").index("pages_idx")
    val referer: Column<String> = text("referer")
    val browser: Column<String> = text("browser")
    val location: Column<String> = text("location")
    val device: Column<String> = text("device")
    val site = reference("site", Sites)
}

class AnalyticsEvent(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<AnalyticsEvent>(AnalyticsEvents)

    var type by AnalyticsEvents.type
    var time by AnalyticsEvents.time
    var page by AnalyticsEvents.page
    var referer by AnalyticsEvents.referer
    var browser by AnalyticsEvents.browser
    var location by AnalyticsEvents.location
    var device by AnalyticsEvents.device
    var site by Site referencedOn AnalyticsEvents.site

    fun toModel() = EventResponse(id.value, type, time, page, referer, browser, location, device)
}