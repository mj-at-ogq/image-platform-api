package me.ogq.ocp.sample.model.image

object ImageFactory {
    fun create(title: String, description: String?, filePath: String): Image {
        val pathConverter = PathConverter()
        val filePathInPath = pathConverter.convertToEntityAttribute(filePath)

        return Image(title, description, ImageFile(filePathInPath, null))
    }
}