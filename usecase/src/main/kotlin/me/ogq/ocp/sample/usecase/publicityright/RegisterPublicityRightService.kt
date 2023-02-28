package me.ogq.ocp.sample.usecase.publicityright

import me.ogq.ocp.sample.model.publicityright.MarketRepository
import me.ogq.ocp.sample.model.publicityright.PublicityRight
import me.ogq.ocp.sample.model.publicityright.PublicityRightRepository
import me.ogq.ocp.sample.usecase.publicityright.command.RegisterPublicityRightCommand
import me.ogq.ocp.sample.usecase.publicityright.dto.RegisterPublicityRightDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
class RegisterPublicityRightService(
    private val publicityRightRepository: PublicityRightRepository,
    private val marketRepository: MarketRepository
) {
    @Transactional
    fun register(cmd: RegisterPublicityRightCommand): RegisterPublicityRightDto {
        fun updateMarkets(publicityRight: PublicityRight, salesMarketIds: Set<String>) {
            val salesMarkets = marketRepository.findAllIn(cmd.salesMarketIds)
            for (market in salesMarkets) {
                market.publicityRight = publicityRight
                publicityRight.salesMarkets.add(market)
            }
            publicityRightRepository.save(publicityRight)
        }

        val publicityRight = publicityRightRepository.findBy(cmd.publicityId)
            ?: throw NoSuchElementException(cmd.publicityId.toString())

        requireNotNull(publicityRight.id) { "publicityRight.id should not be null" }

        updateMarkets(publicityRight, cmd.salesMarketIds)

        return RegisterPublicityRightDto(publicityRight.id!!)
    }
}
