package me.ogq.ocp.sample.model.publicityright

import me.ogq.ocp.sample.model.image.Image
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class PublicityRight(
    @Id
    var id: Long,
    @OneToMany(mappedBy = "publicityRight")
    val salesMarkets: MutableSet<Market> = mutableSetOf(),
) {
    fun attach(markets: Set<Market>) {
        markets.forEach {
            it.publicityRight = this
        }
    }

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
