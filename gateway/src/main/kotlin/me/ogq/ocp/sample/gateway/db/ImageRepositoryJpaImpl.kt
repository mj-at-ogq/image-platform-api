package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.Page
import me.ogq.ocp.sample.model.SlicePage
import me.ogq.ocp.sample.model.image.Image
import me.ogq.ocp.sample.model.image.ImageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class ImageRepositoryJpaImpl(
    private val imageJpaAdapter: ImageJpaAdapter
): ImageRepository {
    override fun save(image: Image) = imageJpaAdapter.save(image)

    override fun findAll(page: Page): SlicePage<Image> {
        val jpaSlice = imageJpaAdapter.findAll(PageRequest.of(page.page, page.pageSize))
        return SlicePage(jpaSlice.hasNext(), jpaSlice.content)
    }


    override fun get(imageId: Long) = imageJpaAdapter.get(imageId)


}