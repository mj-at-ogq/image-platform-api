package me.ogq.ocp.sample.usecase.publicityright

import me.ogq.ocp.sample.model.publicityright.MarketRepository
import me.ogq.ocp.sample.model.publicityright.PublicityRight
import me.ogq.ocp.sample.model.publicityright.PublicityRightRepository
import me.ogq.ocp.sample.usecase.publicityright.command.RegisterPublicityRightCommand
import me.ogq.ocp.sample.usecase.publicityright.dto.RegisterPublicityRightDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterPublicityRightService(
    private val publicityRightRepository: PublicityRightRepository,
    private val marketRepository: MarketRepository
) {
    @Transactional
    fun register(cmd: RegisterPublicityRightCommand): RegisterPublicityRightDto {
        val publicityRight = PublicityRight(cmd.publicityId, mutableSetOf())

        updateMarkets(publicityRight, cmd.salesMarketIds)

        return RegisterPublicityRightDto(publicityRight.id)
    }

    private fun updateMarkets(publicityRight: PublicityRight, salesMarketIds: Set<String>) {
        val salesMarkets = marketRepository.findAllIn(salesMarketIds)
        for (market in salesMarkets) {
            market.publicityRight = publicityRight
            publicityRight.salesMarkets.add(market)
        }
        publicityRightRepository.save(publicityRight)
    }
}
