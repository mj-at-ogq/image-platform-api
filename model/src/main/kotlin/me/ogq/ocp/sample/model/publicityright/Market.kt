package me.ogq.ocp.sample.model.publicityright

import me.ogq.ocp.sample.model.image.Image
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Market(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @ManyToOne
    @JoinColumn(name = "publicity_right_id", referencedColumnName = "id")
    var publicityRight: PublicityRight?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        return id.equals(other.id.toString())
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Market(id='$id')"
    }
}
