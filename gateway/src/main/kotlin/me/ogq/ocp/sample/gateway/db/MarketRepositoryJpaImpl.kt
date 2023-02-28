package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.publicityright.MarketRepository
import org.springframework.stereotype.Repository

@Repository
class MarketRepositoryJpaImpl(
    private val marketJpaAdapter: MarketJpaAdapter
) : MarketRepository {
    override fun findAllIn(marketIds: Set<String>) = marketJpaAdapter.getAllIn(marketIds)
    override fun findBy(marketId: String) = marketJpaAdapter.getBy(marketId)
}
