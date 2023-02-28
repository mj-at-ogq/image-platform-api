package me.ogq.ocp.sample.model.image

interface ImageRepository {
    fun save(image: Image): Image
    fun get(imageId: Long): Image?
}
