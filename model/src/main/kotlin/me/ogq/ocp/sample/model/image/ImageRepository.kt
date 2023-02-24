package me.ogq.ocp.sample.model.image

import me.ogq.ocp.sample.model.Page
import me.ogq.ocp.sample.model.SlicePage


interface ImageRepository {
    fun save(image: Image): Image
    fun findAll(page: Page): SlicePage<Image>
    fun get(imageId: Long): Image?
}