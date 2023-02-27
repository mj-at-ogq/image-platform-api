package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.image.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ImageJpaAdapter : JpaRepository<Image, Long> {
    @Query("SELECT b FROM Image AS b WHERE b.id = ?1")
    fun get(imageId: String): Image?
}
