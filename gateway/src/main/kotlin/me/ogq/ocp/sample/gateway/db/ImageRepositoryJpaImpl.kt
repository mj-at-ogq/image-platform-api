package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.image.Image
import me.ogq.ocp.sample.model.image.ImageRepository
import org.springframework.stereotype.Repository

@Repository
class ImageRepositoryJpaImpl(
    private val imageJpaAdapter: ImageJpaAdapter
): ImageRepository {
    override fun save(image: Image) = imageJpaAdapter.save(image)

    override fun get(imageId: Long) = imageJpaAdapter.get(imageId)
}