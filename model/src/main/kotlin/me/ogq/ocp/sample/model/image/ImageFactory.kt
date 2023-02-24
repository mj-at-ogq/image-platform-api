package me.ogq.ocp.sample.model.image

object ImageFactory {
    fun create(title: String, content: String?): Image {
        return Image(title, content)
    }
}