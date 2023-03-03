@file:Suppress("DEPRECATION")

package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.common.SliceDto
import me.ogq.ocp.sample.model.event.ImageEventData
import me.ogq.ocp.sample.model.publicityright.Market
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.stereotype.Component

@Component
class EsAdapter(
    private val searchRequest: EsRequest,
    private val indexRequest: IndexEsRequest,
    private val parser: EsParser,
    private val client: RestHighLevelClient
) {
    fun save(image: ImageEventData) {
        val indexRequest = indexRequest.createWith(image)

        client.index(indexRequest, RequestOptions.DEFAULT)
    }

    fun searchWith(market: Market, query: String, page: Int, pageSize: Int): SliceDto<ImageEventData> {
        val searchRequest = searchRequest.createWith(market, query, page, pageSize)

        val searchResponse = client.search(searchRequest, RequestOptions.DEFAULT)

        return SliceDto(
            hasNext = hasNext(searchResponse, page, pageSize),
            elements = parser.toImageListFrom(searchResponse)
        )
    }

    private fun hasNext(response: SearchResponse, page: Int, pageSize: Int): Boolean {
        val totalHits = response.hits.totalHits?.value ?: 0
        return totalHits > (page + 1) * pageSize
    }
}
