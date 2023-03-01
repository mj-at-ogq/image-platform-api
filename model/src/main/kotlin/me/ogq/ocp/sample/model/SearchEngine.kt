package me.ogq.ocp.sample.model

import me.ogq.ocp.sample.model.image.Image
import me.ogq.ocp.sample.model.image.ImageData
import me.ogq.ocp.sample.model.publicityright.Market

interface SearchEngine {
    fun save(image: Image)
    fun searchWith(market: Market, query: String): List<ImageData>
}
