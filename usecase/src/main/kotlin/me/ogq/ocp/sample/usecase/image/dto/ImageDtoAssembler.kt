package me.ogq.ocp.sample.usecase.image.dto

import me.ogq.ocp.sample.model.event.ImageData
import me.ogq.ocp.sample.model.image.Image
import java.io.File

object ImageDtoAssembler {
    fun toDto(image: Image): ImageDto {
        requireNotNull(image.id) { "image.id should not be null" }

        return ImageDto(
            id = image.id!!,
            title = image.title,
            description = image.description,
            creatorId = image.authorId.toString(),
            tags = image.tags,
            imagePath = image.file.path.toString(),
            publicityId = image.publicityRightId.toString(),
            imageUrl = generateUrl(image.file.path.toString()),
        )
    }

    fun toDto(image: ImageData): ImageDto {
        requireNotNull(image.id) { "image.id should not be null" }

        return ImageDto(
            id = image.id!!,
            title = image.title,
            description = image.description,
            creatorId = image.authorId.toString(),
            tags = image.tags,
            imagePath = image.imagePath,
            publicityId = image.publicityRightId.toString(),
            imageUrl = generateUrl(image.imagePath)
        )
    }

    private val httpHost = "http://localhost:8080/"
    private fun generateUrl(filePath: String): String {
        fun extractNameFrom(filePath: String): String {
            return File(filePath).name
        }

        val fileName = extractNameFrom(filePath)
        return "$httpHost$fileName"
    }
}
