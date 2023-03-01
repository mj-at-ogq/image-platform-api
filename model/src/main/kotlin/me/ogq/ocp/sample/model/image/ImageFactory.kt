package me.ogq.ocp.sample.model.image

import org.springframework.stereotype.Component

@Component
class ImageFactory(
    private val pathConverter: PathConverter
) {
    fun create(
        title: String,
        description: String?,
        filePath: String,
        creatorId: Long,
        publicityId: Long?,
        tags: Set<String>
    ): Image {
        val filePathInPath = pathConverter.convertToEntityAttribute(filePath)

        return Image(
            title = title,
            description = description,
            file = ImageFile(filePathInPath, null),
            publicityRightId = publicityId,
            authorId = creatorId,
            tags = tags
        )
    }
}
