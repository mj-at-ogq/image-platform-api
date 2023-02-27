package me.ogq.ocp.sample.usecase.image.dto

import me.ogq.ocp.sample.model.image.Image

object ImageDtoAssembler {
    fun toDTO(image: Image): ImageDto {
        return ImageDto(
            imageId = image.id,
            title = image.title,
            description = image.description,
            creatorId = image.authorId.toString(),
            tags = image.tags,
            imagePath = image.file.path.toString(),
            publicityId = image.publicityRightId.toString(),
            imageUrl = image.generateUrl(),
        )
    }
}
