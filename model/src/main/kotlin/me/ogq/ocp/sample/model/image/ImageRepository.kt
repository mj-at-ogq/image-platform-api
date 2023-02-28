package me.ogq.ocp.sample.model.image

interface ImageRepository {
    fun save(image: Image): Image
    fun findBy(imageId: Long): Image?
}
