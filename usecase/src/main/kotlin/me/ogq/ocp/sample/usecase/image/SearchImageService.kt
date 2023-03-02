package me.ogq.ocp.sample.usecase.image

import me.ogq.ocp.sample.model.elasticsearch.SearchEngine
import me.ogq.ocp.sample.model.publicityright.MarketRepository
import me.ogq.ocp.sample.usecase.common.SliceDto
import me.ogq.ocp.sample.usecase.image.command.SearchImageCommand
import me.ogq.ocp.sample.usecase.image.dto.ImageDto
import me.ogq.ocp.sample.usecase.image.dto.ImageDtoAssembler
import org.springframework.stereotype.Service
import java.util.NoSuchElementException

@Service
class SearchImageService(
    private val searchEngine: SearchEngine,
    private val marketRepository: MarketRepository
) {
    fun search(cmd: SearchImageCommand): SliceDto<ImageDto> {
        val market = marketRepository.findBy(cmd.marketId)
            ?: throw NoSuchElementException(cmd.marketId)

        val offset = cmd.page * cmd.pageSize

        // TODO: lazy loading 방식으로 리팩토링
        val images = searchEngine.searchWith(market, cmd.query, 0, 100)
            .map { image -> ImageDtoAssembler.toDTO(image) }
            .toList()

        return SliceDto(
            hasNext = images.drop(offset).size > cmd.pageSize,
            elements = images.drop(offset).take(cmd.pageSize)
        )
    }
}
