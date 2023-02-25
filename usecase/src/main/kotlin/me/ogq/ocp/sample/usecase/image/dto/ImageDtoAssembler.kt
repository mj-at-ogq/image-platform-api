package me.ogq.ocp.sample.usecase.image.dto

import me.ogq.ocp.sample.model.image.Image

object ImageDtoAssembler {
    fun toDTO(image: Image): ImageDto {
        return ImageDto(image.id, image.title, image.description)
    }
}