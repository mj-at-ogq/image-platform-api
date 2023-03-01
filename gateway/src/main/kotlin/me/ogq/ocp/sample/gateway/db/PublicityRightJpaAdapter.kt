package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.publicityright.PublicityRight
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PublicityRightJpaAdapter : JpaRepository<PublicityRight, Long> {
    @Query("SELECT p FROM PublicityRight AS p WHERE p.id = ?1")
    fun getBy(publicityRightId: Long): PublicityRight?
}
