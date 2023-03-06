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
    private val marketRepository: MarketRepository,
) {
    @Transactional
    fun register(cmd: RegisterPublicityRightCommand): RegisterPublicityRightDto {
        val salesMarkets = marketRepository.findAllIn(cmd.salesMarketIds)
        val publicityRight = PublicityRight(cmd.publicityId, mutableSetOf())

        publicityRight.attach(salesMarkets)

        publicityRightRepository.save(publicityRight)

        return RegisterPublicityRightDto(publicityRight.id)
    }
}
