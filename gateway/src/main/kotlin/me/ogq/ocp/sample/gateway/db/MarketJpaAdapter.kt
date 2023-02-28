package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.publicityright.Market
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface MarketJpaAdapter : JpaRepository<Market, String> {
    @Query("SELECT m FROM Market AS m WHERE m.id in ?1")
    fun getAllIn(marketIds: Set<String>): Set<Market>

    @Query("SELECT m FROM Market AS m WHERE m.id = ?1")
    fun getAllBy(marketId: String): Set<Market>
}
