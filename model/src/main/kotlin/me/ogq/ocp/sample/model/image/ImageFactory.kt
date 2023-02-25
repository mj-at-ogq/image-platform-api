package me.ogq.ocp.sample.model.image

import org.springframework.stereotype.Component

@Component
class ImageFactory(
    private val pathConverter: PathConverter
) {
    fun create(title: String, description: String?, filePath: String): Image {
        val filePathInPath = pathConverter.convertToEntityAttribute(filePath)
        return Image(title, description, ImageFile(filePathInPath, null))
    }
}