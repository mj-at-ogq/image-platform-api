package me.ogq.ocp.sample.usecase.image.dto

import me.ogq.ocp.sample.model.event.ImageEventData
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

    fun toDto(image: ImageEventData): ImageDto {
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

    fun toEventData(image: Image): ImageEventData {
        requireNotNull(image.id) { "image.id should not be null" }
        requireNotNull(image.authorId) { "image.authorId should not be null" }

        return ImageEventData(
            id = image.id!!,
            title = image.title,
            description = image.description,
            authorId = image.authorId!!,
            tags = image.tags,
            imagePath = image.file.path.toString(),
            publicityRightId = image.publicityRightId
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
