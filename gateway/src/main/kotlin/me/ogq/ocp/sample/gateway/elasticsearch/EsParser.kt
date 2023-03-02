@file:Suppress("DEPRECATION")

package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.event.ImageEventData
import me.ogq.ocp.sample.model.image.StringSetConverter
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.search.SearchHit
import org.springframework.stereotype.Component

@Component
class EsParser(
    private val tagConverter: StringSetConverter,
) {
    fun toImageListFrom(searchResponse: SearchResponse): List<ImageEventData> {
        val images = mutableListOf<ImageEventData>()
        val hits = searchResponse.hits.hits

        for (hit in hits) {
            val image = toImageFrom(hit)
            images.add(image)
        }

        return images
    }

    private fun toImageFrom(source: SearchHit): ImageEventData {
        val sourceMap = source.sourceAsMap
        val id = sourceMap["image_id"]?.toString()?.toLong()
        val title = sourceMap["title"] as String
        val description = sourceMap["description"] as String?
        val tags = tagConverter.convertToEntityAttribute(sourceMap["tags"] as String)
        val creatorId = sourceMap["creator_id"]?.toString()?.toLong()
        val imagePath = sourceMap["imagePath"] as String
        val publicityRightId =
            if (sourceMap["publicity_id"] == "null") null else sourceMap["publicity_id"]?.toString()?.toLong()

        return ImageEventData(
            id = id,
            title = title,
            description = description,
            tags = tags,
            publicityRightId = publicityRightId,
            authorId = creatorId ?: throw IllegalArgumentException("creatorId should not be null"),
            imagePath = imagePath
        )
    }

    fun toDocumentFrom(image: ImageEventData): Map<String, Any?> {
        return mapOf(
            "image_id" to image.id.toString(),
            "title" to image.title,
            "description" to image.description,
            "imagePath" to image.imagePath,
            "tags" to tagConverter.convertToDatabaseColumn(image.tags),
            "creator_id" to image.authorId.toString(),
            "publicity_id" to image.publicityRightId.toString(),
        )
    }
}
