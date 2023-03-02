package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.event.ImageEventData
import org.elasticsearch.action.index.IndexRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class IndexEsRequest(
    @Value("\${spring.elasticsearch.index}")
    private val indexName: String,
    private val parser: EsParser,
) {
    fun createWith(image: ImageEventData): IndexRequest {
        val doc = parser.toDocumentFrom(image)
        return IndexRequest(indexName).id(doc["image_id"] as String).source(doc)
    }
}
