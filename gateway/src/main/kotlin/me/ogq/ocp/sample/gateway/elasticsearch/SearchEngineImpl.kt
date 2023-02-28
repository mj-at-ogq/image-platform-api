@file:Suppress("DEPRECATION")

package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.SearchEngine
import me.ogq.ocp.sample.model.image.Image
import me.ogq.ocp.sample.model.image.TagStringSetConverter
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SearchEngineImpl(
    private val client: RestHighLevelClient,
    @Value("\${spring.elasticsearch.index}")
    private val indexName: String,
    private val tagConverter: TagStringSetConverter
) : SearchEngine {
    override fun save(image: Image) {
        fun createImageDoc(image: Image): Map<String, Any?> {
            return mapOf(
                "image_id" to image.id.toString(),
                "title" to image.title,
                "description" to image.description,
                "imagePath" to image.file.path.toString(),
                "tags" to tagConverter.convertToDatabaseColumn(image.tags),
                "creator_id" to image.authorId.toString(),
                "publicity_id" to image.publicityRightId.toString(),
            )
        }

        val doc: Map<String, Any?> = createImageDoc(image)

        client.index(
            IndexRequest(indexName).id(doc["image_id"] as String).source(doc),
            RequestOptions.DEFAULT
        )
    }
}
