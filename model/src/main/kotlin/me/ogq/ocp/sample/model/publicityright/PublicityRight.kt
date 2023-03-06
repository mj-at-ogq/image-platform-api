package me.ogq.ocp.sample.model.publicityright

import me.ogq.ocp.sample.model.image.Image
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class PublicityRight(
    @Id // 클라이언트로부터 ID를 전달 받는 것이 비지니스 요구사항임
    var id: Long,
    @OneToMany(mappedBy = "publicityRight", cascade = [CascadeType.ALL])
    val salesMarkets: MutableSet<Market> = mutableSetOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "PublicityRight(id='$id')"
    }
}
