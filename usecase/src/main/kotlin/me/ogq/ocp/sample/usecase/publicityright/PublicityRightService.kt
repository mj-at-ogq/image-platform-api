package me.ogq.ocp.sample.usecase.publicityright

import me.ogq.ocp.sample.model.publicityright.PublicityRightRepository
import me.ogq.ocp.sample.usecase.publicityright.command.GetDetailPublicityRightCommand
import me.ogq.ocp.sample.usecase.publicityright.dto.PublicityRightDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
class PublicityRightService(
    private val publicityRightRepository: PublicityRightRepository,
) {
    @Transactional(readOnly = true)
    fun get(cmd: GetDetailPublicityRightCommand): PublicityRightDto {
        val publicityRight = publicityRightRepository.findBy(cmd.publicityRightId)
            ?: throw NoSuchElementException(cmd.publicityRightId.toString())

        val result = publicityRight.salesMarkets
            .map { market -> market.id }
            .toMutableSet()

        return PublicityRightDto(result)
    }
}
