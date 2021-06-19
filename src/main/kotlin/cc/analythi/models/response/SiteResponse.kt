package cc.analythi.models.response

import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotNull
import org.valiktor.validate
import java.util.*

data class SiteResponse(
    val id: UUID,
    val name: String,
    val events: List<EventResponse>? = listOf(),
) {
    fun validate() {
        validate(this) {
            validate(SiteResponse::name).isNotBlank().isNotNull()
        }
    }

    init {
        validate()
    }
}
