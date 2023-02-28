package me.ogq.ocp.sample.gateway.db

import me.ogq.ocp.sample.model.publicityright.PublicityRight
import me.ogq.ocp.sample.model.publicityright.PublicityRightRepository
import org.springframework.stereotype.Repository

@Repository
class PublicityRightRepositoryJpaImpl(
    private val publicityRightJpaAdapter: PublicityRightJpaAdapter
) : PublicityRightRepository {
    override fun findBy(publicityRightId: Long) = publicityRightJpaAdapter.getBy(publicityRightId)

    override fun save(publicityRight: PublicityRight) = publicityRightJpaAdapter.save(publicityRight)
}
