@file:Suppress("DEPRECATION")

package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.common.SliceDto
import me.ogq.ocp.sample.model.elasticsearch.SearchEngine
import me.ogq.ocp.sample.model.event.ImageEventData
import me.ogq.ocp.sample.model.publicityright.Market
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.stereotype.Component

@Component
class SearchEngineImpl(
    private val searchRequest: EsRequest,
    private val indexRequest: IndexEsRequest,
    private val parser: EsParser,
    private val client: RestHighLevelClient
) : SearchEngine {
    override fun save(image: ImageEventData) {
        val indexRequest = indexRequest.createWith(image)

        client.index(indexRequest, RequestOptions.DEFAULT)
    }

    override fun searchWith(market: Market, query: String, page: Int, pageSize: Int): SliceDto<ImageEventData> {
        val searchRequest = searchRequest.createWith(market, query, page, pageSize)

        val searchResponse = client.search(searchRequest, RequestOptions.DEFAULT)

        val totalHits = searchResponse.hits.totalHits?.value ?: 0
        val hasNext = totalHits > (page + 1) * pageSize

        return SliceDto(
            hasNext = hasNext,
            elements = parser.toImageListFrom(searchResponse)
        )
    }
}
