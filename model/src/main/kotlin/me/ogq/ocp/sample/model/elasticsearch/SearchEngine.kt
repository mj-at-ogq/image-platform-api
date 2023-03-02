package me.ogq.ocp.sample.model.elasticsearch

import me.ogq.ocp.sample.model.event.ImageData
import me.ogq.ocp.sample.model.image.Image
import me.ogq.ocp.sample.model.publicityright.Market

interface SearchEngine {
    fun save(image: Image)
    fun searchWith(market: Market, query: String, page: Int, pageSize: Int): List<ImageData>
}
