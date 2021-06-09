package cc.analythi.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.*

object Sites : UUIDTable("sites", "id") {
    val name: Column<String> = text("name")
}

class Site(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Site>(Sites)

    var name by Sites.name
    val events by AnalyticsEvent referrersOn AnalyticsEvents.site
}