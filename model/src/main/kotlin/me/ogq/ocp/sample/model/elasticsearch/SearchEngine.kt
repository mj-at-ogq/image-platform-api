package me.ogq.ocp.sample.model.elasticsearch

import me.ogq.ocp.sample.model.common.SliceDto
import me.ogq.ocp.sample.model.event.ImageEventData
import me.ogq.ocp.sample.model.publicityright.Market

interface SearchEngine {
    fun save(image: ImageEventData)
    fun searchWith(market: Market, query: String, page: Int, pageSize: Int): SliceDto<ImageEventData>
}
