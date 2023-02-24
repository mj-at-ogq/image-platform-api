package me.ogq.ocp.sample.usecase.image.dto

import me.ogq.ocp.sample.model.image.Image

object ImageDtoAssembler {
    fun toDTO(image: Image): ImageDto {
        return ImageDto(image.getIdStr(), image.title, image.content)
    }
    fun toDTOList(images: List<Image>): List<ImageDto> {
        return images.map { toDTO(it) }
    }
}