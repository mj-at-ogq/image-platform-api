package me.ogq.ocp.sample.model.publicityright

interface MarketRepository {
    fun findAllIn(marketIds: Set<String>): Set<Market>
    fun findBy(marketId: String): Market?
}
