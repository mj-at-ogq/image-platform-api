package me.ogq.ocp.sample.gateway.elasticsearch

import me.ogq.ocp.sample.model.elasticsearch.SearchEngine
import me.ogq.ocp.sample.model.event.ImageEventData
import me.ogq.ocp.sample.model.publicityright.Market
import org.springframework.stereotype.Component

@Component
class SearchEngineImpl(
    private val esAdapter: EsAdapter
) : SearchEngine {
    override fun save(image: ImageEventData) = esAdapter.save(image)

    override fun searchWith(market: Market, query: String, page: Int, pageSize: Int) =
        esAdapter.searchWith(market, query, page, pageSize)
}
