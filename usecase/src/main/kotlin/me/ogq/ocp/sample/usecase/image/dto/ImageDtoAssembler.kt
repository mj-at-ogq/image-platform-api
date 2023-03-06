package me.ogq.ocp.sample.usecase.image.dto

import me.ogq.ocp.sample.model.event.ImageEventData
import me.ogq.ocp.sample.model.image.Image
import me.ogq.ocp.sample.model.image.toUrl
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ImageDtoAssembler(
    @Value("\${const.http-host}")
    private val httpHost: String,
) {
    fun toDto(image: Image): ImageDto {
        return ImageDto(
            id = image.id!!,
            title = image.title,
            description = image.description,
            creatorId = image.authorId.toString(),
            tags = image.tags,
            imagePath = image.file.path.toString(),
            publicityId = image.publicityRightId.toString(),
            imageUrl = image.file.toUrl(httpHost),
        )
    }

    fun toDto(image: ImageEventData): ImageDto {
        return ImageDto(
            id = image.id!!,
            title = image.title,
            description = image.description,
            creatorId = image.authorId.toString(),
            tags = image.tags,
            imagePath = image.imagePath,
            publicityId = image.publicityRightId.toString(),
            imageUrl = image.imagePath.toUrl(httpHost),
        )
    }

    fun toEventData(image: Image): ImageEventData {
        return ImageEventData(
            id = image.id!!,
            title = image.title,
            description = image.description,
            authorId = image.authorId!!,
            tags = image.tags,
            imagePath = image.file.path.toString(),
            publicityRightId = image.publicityRightId,
        )
    }
}
