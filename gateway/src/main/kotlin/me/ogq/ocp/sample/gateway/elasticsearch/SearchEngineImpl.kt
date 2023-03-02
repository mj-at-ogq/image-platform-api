@file:Suppress("DEPRECATION")

package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.SearchEngine
import me.ogq.ocp.sample.model.image.Image
import me.ogq.ocp.sample.model.image.ImageData
import me.ogq.ocp.sample.model.image.TagStringSetConverter
import me.ogq.ocp.sample.model.publicityright.Market
import me.ogq.ocp.sample.model.publicityright.PublicityRight
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.index.query.Operator
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SearchEngineImpl(
    @Value("\${spring.elasticsearch.index}")
    private val indexName: String,
    private val client: RestHighLevelClient,
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

    override fun searchWith(market: Market, query: String, page: Int, pageSize: Int): List<ImageData> {
        fun generateSearchRequest(publicityRight: PublicityRight?, query: String): SearchRequest {
            val queryBuilder = QueryBuilders.boolQuery()

            queryBuilder.must(
                QueryBuilders.queryStringQuery("*$query*")
                    .field("title")
                    .field("tags")
                    .lenient(true)
                    .defaultOperator(Operator.OR)
            )

            if (publicityRight != null) {
                queryBuilder.should(QueryBuilders.termQuery("publicity_id", publicityRight.id.toString()))
            } else {
                queryBuilder.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery("publicity_id")))
            }

            val sourceBuilder = SearchSourceBuilder()
            sourceBuilder.query(queryBuilder)
            sourceBuilder.from(page * pageSize)
            sourceBuilder.size(pageSize)

            return SearchRequest(indexName).source(sourceBuilder)
        }

        fun parseFrom(searchResponse: SearchResponse): List<ImageData> {
            val hits = searchResponse.hits.hits
            val images = mutableListOf<ImageData>()

            for (hit in hits) {
                val sourceMap = hit.sourceAsMap
                val id = sourceMap["image_id"]?.toString()?.toLong()
                val title = sourceMap["title"] as String
                val description = sourceMap["description"] as String?
                val tags = tagConverter.convertToEntityAttribute(sourceMap["tags"] as String)
                val creatorId = sourceMap["creator_id"]?.toString()?.toLong()
                val imagePath = sourceMap["imagePath"] as String
                val publicityRightId = if (sourceMap["publicity_id"] == "null") null else sourceMap["publicity_id"]?.toString()?.toLong()

                val image = ImageData(
                    id = id,
                    title = title,
                    description = description,
                    tags = tags,
                    publicityRightId = publicityRightId,
                    authorId = creatorId ?: throw IllegalArgumentException("creatorId should not be null"),
                    imagePath = imagePath
                )

                images.add(image)
            }

            return images
        }

        val searchRequest = generateSearchRequest(market.publicityRight, query)

        val searchResponse = client.search(searchRequest, RequestOptions.DEFAULT)

        return parseFrom(searchResponse)
    }
}
