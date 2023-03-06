package me.ogq.ocp.sample.model.publicityright

import me.ogq.ocp.sample.model.image.Image
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.TableGenerator

@Entity
@TableGenerator(
    name = "market_seq_generator",
    table = "market_sequences",
    pkColumnName = "sequence",
    pkColumnValue = "market",
    valueColumnName = "value",
    allocationSize = 100,
)
class Market(
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "market_seq_generator")
    val id: String = "",
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
