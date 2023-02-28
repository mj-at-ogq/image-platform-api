package me.ogq.ocp.sample.model.publicityright

interface PublicityRightRepository {
    fun save(publicityRight: PublicityRight): PublicityRight
    fun findBy(publicityRightId: Long): PublicityRight?
}
