package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.image.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ImageJpaAdapter : JpaRepository<Image, Long> {
    @Query("SELECT i FROM Image AS i WHERE i.id = ?1")
    fun getBy(imageId: Long): Image?
}
