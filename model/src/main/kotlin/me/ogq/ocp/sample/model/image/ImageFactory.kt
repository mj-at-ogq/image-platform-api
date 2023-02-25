package me.ogq.ocp.sample.model.image

object ImageFactory {
    fun create(title: String, description: String?): Image {
        return Image(title, description)
    }
}